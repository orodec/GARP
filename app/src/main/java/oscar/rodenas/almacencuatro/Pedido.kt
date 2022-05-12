package oscar.rodenas.almacencuatro

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pedido.*
import kotlinx.android.synthetic.main.activity_ubicar_palet.*


class Pedido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        btn_sacar_buscar.setOnClickListener{
            //Leer la referencia
            //conectar a bd y hacer busqueda por referencia del palet devolverlo en orden de fecha ascendente


        }

        btn_sacar_buscar.setOnClickListener{
// hacer una busqueda por la referencia introducida

            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase

            val cursor = bd.rawQuery("select fecha, sscc, almacen, estanteria, posicion from almacen where referencia='${et_sacar_referencia.text.toString()}' order by fecha DESC", null)

            if(cursor.moveToLast()){

            tv_sacar_sscc2.setText("${cursor.getString(1)}")
            tv_sacar_fecha.setText("fecha: ${cursor.getString(0)}")
            tv_sacar_ubicacion.setText("Almacen: ${cursor.getString(2)}, Estantería: ${cursor.getString(3)}, Posición: ${cursor.getString(4)}")
            bd.close()

            }else {
                tv_sacar_sscc2.setText("")
                tv_sacar_fecha.setText("fecha")
                tv_sacar_ubicacion.setText(("Ubicación"))
                val toast4: Toast = Toast.makeText(this, "Referencía no encontrada en el almacen", Toast.LENGTH_SHORT)
                toast4.setGravity(Gravity.CENTER, 0, 0)
                toast4.show()
            }
        }

        btn_sacar_retirar.setOnClickListener{
           if(tv_sacar_sscc2.text.toString() !=null) {
               val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
               val bd = admin.writableDatabase
               bd.execSQL("UPDATE almacen SET sscc=null, fecha=null, referencia=null WHERE sscc='${tv_sacar_sscc2.text.toString()}';")
               bd.close()
               val toast5: Toast = Toast.makeText(this, "El palet ha sido retirado del almacen, la ubicación queda libre", Toast.LENGTH_SHORT)
               toast5.setGravity(Gravity.CENTER, 0, 0)
               toast5.show()
           }else{
               val toast4: Toast = Toast.makeText(this, "Referencia no encontrada en el almacen", Toast.LENGTH_SHORT)
               toast4.setGravity(Gravity.CENTER, 0, 0)
               toast4.show()
           }
        }
        btn_sacar_volver.setOnClickListener{
            finish()
        }
    }
}