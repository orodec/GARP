package oscar.rodenas.almacencuatro

import android.util.Log
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_ubicar_palet.*

class buscarCalleVacia {
/*
    fun CalleVacia(){
        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase

        val cursor = bd.rawQuery("select almacen, numestanterias, numposiciones from datosal", null)
        cursor.moveToFirst()

        var almacen = cursor.getString(0)
        var numestanterias = cursor.getString(1)
        var numposiciones = cursor.getString(2)


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
    } */
}