package oscar.rodenas.almacencuatro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_ubicar_palet.*


class UbicarPalet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicar_palet)


        btn_escanear.setOnClickListener{(initScanner ())}

        btn_ubicar.setOnClickListener {

            tv_almacen.setText("Almacen: ")
            tv_estanteria.setText("Estantería: ")
            tv_posicion.setText("Posición: ")

            if ((et_referencia.text.toString() != "") and (et_fecha.text.toString() != "") and (et_sscc.text.toString() != "")) {


                val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
                val bd = admin.writableDatabase

                val cursor = bd.rawQuery("select almacen, numestanterias, numposiciones from datosal", null)
                cursor.moveToFirst()

                var paletUbicado = false

                do {


                    var almacen = cursor.getString(0)
                    var numestanterias = cursor.getString(1)
                    var numposiciones = cursor.getString(2)

                    Log.d("almacen", almacen)
                    Log.d("numero de estanterias", numestanterias)
                    Log.d("posiciones", numposiciones)

                    //Busco un palet de la misma referencia y la misma fecha
                    val fila = bd.rawQuery(
                            "select almacen,estanteria,posicion from almacen where referencia='${et_referencia.text.toString()}'and fecha='${et_fecha.text.toString()}'",
                            null)
                    if (fila.moveToFirst()) {
                        // sin encuentra algun palet
                        val paralog = fila.moveToLast()

                        Log.d("Palet similar", paralog.toString())

                        val ubicacion = fila.getString(2).toInt() + 1

                        //compruebo que tengo sitio o no en la misma estanteria
                        if (ubicacion > numposiciones.toInt()) {
                            //busco una estanteria vacia
                            Log.d("estoy", "buscando estanteria vacia, porque estaba llena la anterior")
                            for (num in 1..numestanterias.toInt()) {
                                val fila = bd.rawQuery(
                                        "select posicion from almacen where almacen='${almacen.toString()}' and estanteria='${num}' and (sscc is null) ",
                                        null
                                )
                                if (fila.getCount() == numposiciones.toInt()) {
                                    // la estanteria num esta vacia, ahora tengo que registrar el palet en esa ubicacion
                                    bd.execSQL("UPDATE almacen SET sscc=${et_sscc.text.toString()}, fecha='${et_fecha.text.toString()}', referencia=${et_referencia.text.toString()} WHERE almacen='${almacen}' and estanteria=${num} and posicion=1;")
                                    bd.close()
                                    tv_almacen.setText("Almacen: ${almacen}")
                                    tv_estanteria.setText("Estantería: ${num}")
                                    tv_posicion.setText("Posición: 1")
                                    val toast4: Toast = Toast.makeText(this, "Palet ubicado correctamente", Toast.LENGTH_SHORT)
                                    toast4.setGravity(Gravity.CENTER, 0, 0)
                                    toast4.show()
                                    paletUbicado = true
                                    break
                                }
                            }
                        } else {
                            // Si que hay hueco en la misma estanteria
                            Log.d("VOy a escribir", "el palet")
                            Log.d("where", "almacen='${fila.getString(0)}' and estanteria=${fila.getString(1)} and posicion=${ubicacion}")
                            Log.d("set", "sscc=${et_sscc.text.toString()}, fecha='${et_fecha.text.toString()}', referencia=${et_referencia.text.toString()}")
                            bd.execSQL("UPDATE almacen SET sscc=${et_sscc.text.toString()}, fecha='${et_fecha.text.toString()}', referencia=${et_referencia.text.toString()} WHERE almacen='${fila.getString(0)}' and estanteria=${fila.getString(1)} and posicion=${ubicacion};")
                            bd.close()
                            tv_almacen.setText("Almacen: ${fila.getString(0)}")
                            tv_estanteria.setText("Estantería: ${fila.getString(1)}")
                            tv_posicion.setText("Posición: ${ubicacion}")
                            val toast4: Toast = Toast.makeText(this, "Palet ubicado correctamente", Toast.LENGTH_SHORT)
                            toast4.setGravity(Gravity.CENTER, 0, 0)
                            toast4.show()
                            paletUbicado = true

                        }

                    } else {
                        // si no encuentra tengo que ubicarlo en una estanteria vacia
                        Log.d("No habia similar", "Estoy buscando vacia")
                        for (num in 1..numestanterias.toInt()) {
                            Log.d("numero de ieracion", num.toString())

                            val fila = bd.rawQuery("select posicion from almacen where almacen='${almacen.toString()}' and estanteria=${num} and (sscc is null)", null)
                            Log.d("Numro de pos", fila.getCount().toString())
                            Log.d("almacen", almacen.toString())


                            if (fila.getCount() == numposiciones.toInt()) {
                                // la estanteria num esta vacia, ahora tengo que registrar el palet en esa ubicacion
                                bd.execSQL("UPDATE almacen SET sscc=${et_sscc.text.toString()}, fecha='${et_fecha.text.toString()}', referencia=${et_referencia.text.toString()} WHERE almacen='${almacen}' and estanteria=${num} and posicion=1;")

                                bd.close()
                                tv_almacen.setText("Almacen: ${almacen}")
                                tv_estanteria.setText("Estantería: ${num}")
                                tv_posicion.setText("Posición: 1")
                                val toast4: Toast = Toast.makeText(this, "Palet ubicado correctamente", Toast.LENGTH_SHORT)
                                toast4.setGravity(Gravity.CENTER, 0, 0)
                                toast4.show()
                                paletUbicado = true
                                break
                            }
                        }
                    }


                    if (paletUbicado) break
                    Log.d("estoy", "justo antes del while")
                } while (cursor.moveToNext())
                Log.d("estoy", "justo despues del while")
                Log.d("valor de palet ubicado", paletUbicado.toString())
                if (!paletUbicado) {
                    val toast4: Toast = Toast.makeText(this, "No hay ubicación disponible para este palet", Toast.LENGTH_SHORT)
                    toast4.setGravity(Gravity.CENTER, 0, 0)
                    toast4.show()
                }
            } else {
                val toast4: Toast = Toast.makeText(this, "Introduzca todos los datos correctamente", Toast.LENGTH_SHORT)
                toast4.setGravity(Gravity.CENTER, 0, 0)
                toast4.show()
            }
        }

        btn_volver3.setOnClickListener {

            finish()
        }
    }

    private fun initScanner() {


            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            integrator.setPrompt("Escanea el sscc")
            integrator.setTorchEnabled(false)
            integrator.setBeepEnabled(true)
            integrator.initiateScan()




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {

                val toast7: Toast = Toast.makeText(this, "Escaneo Cancelado", Toast.LENGTH_LONG)
                toast7.setGravity(Gravity.CENTER, 0, 0)
                toast7.show()

            } else {


                val toast6: Toast = Toast.makeText(this, "El valor escaneado es: " + result.contents, Toast.LENGTH_SHORT)
                toast6.setGravity(Gravity.CENTER, 0, 0)
                toast6.show()

                et_sscc.setText(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

