package oscar.rodenas.almacencuatro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

 // cambio de prueba para git

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener{
            val i = Intent(this, UbicarPalet::class.java)
            startActivity(i)
        }

        btn_pedido.setOnClickListener{
            val i = Intent(this, Pedido::class.java)
            startActivity(i)

        }

        btn_crear_almacen.setOnClickListener{
            val i = Intent(this, CrearAlmacen::class.java)
            startActivity(i)
        }

        btn_vista.setOnClickListener{
            val i = Intent(this, Vista::class.java)
            startActivity(i)
        }

    }
}