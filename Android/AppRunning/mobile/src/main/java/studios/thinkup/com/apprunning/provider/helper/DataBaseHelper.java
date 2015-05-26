package studios.thinkup.com.apprunning.provider.helper;

/**
 * Created by fcostazini on 26/05/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    //The Android's default system path of your application database.

    private final static String DB_NAME = "runningDB.sqlite";
    private final static int DB_VERSION = 1;


    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context contexto
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;

    }

    @Override
    public SQLiteDatabase getReadableDatabase() {

            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return super.getReadableDatabase();
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase() {

        SQLiteDatabase checkDB;

        try {
            String path = getPath();
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        } catch (Exception e) {
            checkDB = null;
        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null;
    }

    private String getPath() {
        return myContext.getDatabasePath(DB_NAME).getPath();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {


        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName;

        if(android.os.Build.VERSION.SDK_INT >= 17) {
            outFileName = myContext.getApplicationInfo().dataDir + "/databases/";
        }
        else {
            outFileName = "/data/data/" + myContext.getPackageName() + "/databases/";
        }


        //Open the empty db as the output stream
        // create a File object for the parent directory

        File wallpaperDirectory = new File(outFileName);
        // have the object build the directory structure, if needed.
        wallpaperDirectory.mkdirs();
        // create a File object for the output file
        File outputFile = new File(wallpaperDirectory, DB_NAME);
        // now attach the OutputStream to the file object, instead of a String representation
        FileOutputStream fos = new FileOutputStream(outputFile);

        //OutputStream myOutput = new FileOutputStream(outFileName+DB_NAME);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            fos.write(buffer, 0, length);
        }

        //Close the streams
        fos.flush();
        fos.close();
        myInput.close();

    }
    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}