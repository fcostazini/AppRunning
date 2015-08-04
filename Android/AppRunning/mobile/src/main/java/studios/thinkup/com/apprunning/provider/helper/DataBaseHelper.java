package studios.thinkup.com.apprunning.provider.helper;

/**
 * Created by fcostazini on 26/05/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class DataBaseHelper extends SQLiteOpenHelper {
    //The Android's default system path of your application database.

    private final static String DATABASE_NAME = "runningDB.sqlite";
    private final static int DATABASE_VERSION = 331;


    private SQLiteDatabase myDataBase;

    private Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context contexto
     */
    public DataBaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            this.copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method will create database in application package /databases
     * directory when first time application launched
     */
    public void createDataBase() throws IOException {
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException mIOException) {
                mIOException.printStackTrace();
                throw new Error("Error copying database");
            } finally {
                this.close();
            }
        }
    }

    /**
     * This method checks whether database is exists or not *
     */
    private boolean checkDataBase() {
        try {
            final String mPath = this.myContext.getDatabasePath(DATABASE_NAME).getPath();
            final File file = new File(mPath);
            return file.exists();
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method will copy database from /assets directory to application
     * package /databases directory
     */
    private void copyDataBase() throws IOException {
        try {

            InputStream mInputStream = myContext.getAssets().open(DATABASE_NAME);
            String outFileName = this.myContext.getDatabasePath(DATABASE_NAME).getPath();
            OutputStream mOutputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInputStream.read(buffer)) > 0) {
                mOutputStream.write(buffer, 0, length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method open database for operations *
     */

    public boolean openDataBase() throws SQLException {
        String mPath = this.myContext.getDatabasePath(DATABASE_NAME).getPath();
        myDataBase = SQLiteDatabase.openDatabase(mPath, null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS );
        return myDataBase.isOpen();
    }

    /**
     * This method close database connection and released occupied memory *
     */
    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }
}