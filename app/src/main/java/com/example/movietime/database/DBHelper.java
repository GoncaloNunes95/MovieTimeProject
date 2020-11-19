package com.example.movietime.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static int versao = 1;
    private static String nome = "MovieTime.db";

    public DBHelper(Context context) {
        super(context, nome, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE Utilizador(Username TEXT PRIMARY KEY, Email TEXT, Password TEXT);";
        db.execSQL(str);

        String str1 = "CREATE TABLE FavoriteMovies(AccountUser TEXT, IdMovie INTEGER PRIMARY KEY, title TEXT, poster TEXT, date TEXT, " +
                "average TEXT, sinopse TEXT, FOREIGN KEY (AccountUser) REFERENCES Utilizador(Username));";
        db.execSQL(str1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Utilizador;");
        onCreate(db);
    }

    public long CriarFavorito(String AccountUser, int id, String title, String poster, String date, String average, String sinopse) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("AccountUser", AccountUser);
        cv.put("IdMovie", id);
        cv.put("title", title);
        cv.put("poster", poster);
        cv.put("date", date);
        cv.put("average", average);
        cv.put("sinopse", sinopse);
        long res = db.insert("FavoriteMovies", null, cv);
        return res;
    }

    public String ValidarExistenciaFavorito(String username, int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("FavoriteMovies", null, "AccountUser=? AND IdMovie=?", new String[]{username, String.valueOf(id)}, null, null, null, null);

        if (c.getCount() > 0) {
            return "1";
        }

        return "0";
    }

    public void RemoveFavorito(String username, int id) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete("FavoriteMovies", "AccountUser=? AND IdMovie=?", new String[]{username, String.valueOf(id)});
    }

    public String SelectFavoritosUser(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("FavoriteMovies", null, "AccountUser=?", new String[]{username}, null, null, null, null);

        if (c.getCount() > 0) {
            return "1";
        }

        return "0";
    }

    public Cursor mostrarFavoritos(String AccountUser) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("FavoriteMovies", null, "AccountUser=?", new String[]{AccountUser}, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    public long CriarUtilizador(String username, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Username", username);
        cv.put("Email", email);
        cv.put("Password", password);
        long result = db.insert("Utilizador", null, cv);
        return result;
    }

    public long Update_Dados_Pessoais(String username, String password, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Username", username);
        cv.put("Email", email);
        cv.put("Password", password);
        String where = "Username=?";

        long result = db.update("Utilizador", cv, where, new String[]{username});
        return result;
    }

    public String Validar_Dados_Pessoais(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Utilizador", null, "Username=?", new String[]{username}, null, null, null, null);

        if (c.getCount() > 0) {
            return "OK";
        }
        return "ERRO";
    }

    public String ValidarLogin(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Utilizador", null, "Username=? AND Password=?", new String[]{username, password}, null, null, null, null);

        if (c.getCount() > 0) {
            return "OK";
        }

        return "ERRO";
    }

    public Cursor mostrarRegisto(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("Utilizador", null, "Username=? AND Password=? ", new String[]{username, password}, null, null, null, null);

        c.moveToFirst();
        return c;
    }
}
