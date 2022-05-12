package oscar.rodenas.almacencuatro

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class AdminSQLiteOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table datosal(almacen text primary key, numestanterias int, numposiciones int)")
        db.execSQL("create table almacen(id INTEGER PRIMARY KEY AUTOINCREMENT, almacen text, estanteria int, posicion int, sscc int, referencia int, fecha text, unique(almacen, estanteria, posicion), unique(sscc), FOREIGN KEY(almacen) REFERENCES datosal(almacen))")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}