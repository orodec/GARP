package oscar.rodenas.almacencuatro

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_crear_almacen.*

class CrearAlmacen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_almacen)

        btn_crear.setOnClickListener {
            if((et_nombre.text.toString() !="") and (et_estanterias.text.toString() !="") and(et_posicion.text.toString() !="")) {
                val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
                val bd = admin.writableDatabase
                val registro = ContentValues()
                val registro2 = ContentValues()

                val numEstanteria = et_estanterias.getText().toString()
                val finalEstanteria = Integer.parseInt(numEstanteria)
                val numPosicion = et_posicion.getText().toString()
                val finalPosicion = Integer.parseInt(numPosicion)

                registro2.put("almacen", et_nombre.getText().toString())
                registro2.put("numestanterias", et_estanterias.getText().toString())
                registro2.put("numposiciones", et_posicion.getText().toString())
                bd.insert("datosal", null, registro2)

                for (num in 1..finalEstanteria) {
                    for (numpos in 1..finalPosicion) {
                        registro.put("almacen", et_nombre.getText().toString())
                        registro.put("posicion", numpos.toString())
                        registro.put("estanteria", num.toString())
                        bd.insert("almacen", null, registro)
                    }
                }


                bd.close()
                et_nombre.setText("")
                et_estanterias.setText("")
                et_posicion.setText("")
                val toast4: Toast = Toast.makeText(this, "Almacen creado correctamente", Toast.LENGTH_SHORT)
                toast4.setGravity(Gravity.CENTER, 0, 0)
                toast4.show()
            }else{
                val toast4: Toast = Toast.makeText(this, "Introduzca todos los datos necesarios", Toast.LENGTH_SHORT)
                toast4.setGravity(Gravity.CENTER, 0, 0)
                toast4.show()
            }
        }

        btn_volver2.setOnClickListener{
            finish()
        }

    }

}