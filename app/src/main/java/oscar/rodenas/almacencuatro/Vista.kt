package oscar.rodenas.almacencuatro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_vista.*

class Vista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista)

        val ranking = ArrayList<String>()


        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase

        val fila = bd.rawQuery("select * from almacen", null)

        if(fila.moveToFirst()){
            do{
                ranking.add(fila.getString(0)
                        + " Almacen: " + fila.getString(1)
                        + " Calle: " + fila.getString(2)
                        + " Posicion: " + fila.getString(3)
                        + " \n sscc: " + fila.getString(4)
                        + " referencia: " + fila.getString(5)
                        + " fecha: " + fila.getString(6))
            }while(fila.moveToNext())
        }
        bd.close();

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ranking)

        lv1.adapter = adapter;




        btn_vista_volver.setOnClickListener{
            finish()
        }
    }
}