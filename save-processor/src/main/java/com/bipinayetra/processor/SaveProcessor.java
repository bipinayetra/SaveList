package com.bipinayetra.processor;

import com.bipinayetra.annotation.SaveList;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.*;

public class SaveProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {

        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(SaveList.class.getCanonicalName());

        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        Collection<? extends Element> annotatedElements = roundEnvironment
                .getElementsAnnotatedWith(SaveList.class);
        if(annotatedElements.size()==0)return true;

        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package com.bipinayetra.savelist;\n" +
                    "\n" +
                    "import android.content.ContentValues;\n" +
                    "import android.content.Context;\n" +
                    "import android.database.Cursor;\n" +
                    "import android.database.sqlite.SQLiteDatabase;\n" +
                    "import android.database.sqlite.SQLiteOpenHelper;\n" +
                    "import android.provider.BaseColumns;\n" +
                    "import com.google.gson.Gson;\n" +
                    "import com.google.gson.reflect.TypeToken;\n" +
                    "\n" +
                    "import java.io.Serializable;\n" +
                    "import java.lang.reflect.Type;\n" +
                    "import java.util.HashMap;\n" +
                    "import java.util.List;\n" +
                    "import java.util.Map;\n" +
                    "\n" +
                    "public class EscapeSQLBoiler {\n" +
                    "\n" +
                    "    private static Context lcontext;\n" +
                    "    private static EscapeSQLBoiler escapeSQLBoiler;\n" +
                    "    private final String SQL_CREATE_ENTRIES =\n" +
                    "            \"CREATE TABLE \" + Contract.Entry.TABLE_NAME + \" (\" +\n" +
                    "                    Contract.Entry._ID + \" INTEGER PRIMARY KEY,\" +\n" +
                    "                    Contract.Entry.COLUMN_NAME_KEY + \" TEXT,\" +\n" +
                    "                    Contract.Entry.COLUMN_NAME_VALUE + \" TEXT)\";\n" +
                    "    private final String SQL_DELETE_ENTRIES =\n" +
                    "            \"DROP TABLE IF EXISTS \" + Contract.Entry.TABLE_NAME;\n" +
                    "    Map<String, Class> mapOfListType = new HashMap<>();\n" +
                    "    SaveListDbHelper saveListDbHelper;\n" +
                    "    SQLiteDatabase db;\n" +
                    "\n" +
                    "    private EscapeSQLBoiler() {\n" +
                    "        saveListDbHelper = new SaveListDbHelper(lcontext);\n" +
                    "        db = saveListDbHelper.getWritableDatabase();");
                    writeMapofKeyandType(annotations, roundEnvironment, stringBuilder);
                    stringBuilder.append("}\n" +
                            "\n" +
                            "    public static EscapeSQLBoiler getEscapeSQLBoiler(Context context) {\n" +
                            "        lcontext = context;\n" +
                            "        if (escapeSQLBoiler == null) {\n" +
                            "            escapeSQLBoiler = new EscapeSQLBoiler();\n" +
                            "        }\n" +
                            "        return escapeSQLBoiler;\n" +
                            "    }\n" +
                            "\n" +
                            "    private Class getMapOfListType(String keyname) {\n" +
                            "        Class aClass = mapOfListType.get(keyname);\n" +
                            "        return aClass;\n" +
                            "    }\n" +
                            "\n" +
                            "    public <T> List<T> giveMyListSavedInKey(String keyname) {\n" +
                            "        return giveMyListSavedInKey(keyname, getMapOfListType(keyname));\n" +
                            "    }\n" +
                            "\n" +
                            "    private <T> List<T> giveMyListSavedInKey(String keyname, final Class clazz) {\n" +
                            "        String[] projection = {\n" +
                            "                Contract.Entry.COLUMN_NAME_KEY,\n" +
                            "                Contract.Entry.COLUMN_NAME_VALUE\n" +
                            "        };\n" +
                            "\n" +
                            "        String selection = Contract.Entry.COLUMN_NAME_KEY + \" = ?\";\n" +
                            "        String[] selectionArgs = {keyname};\n" +
                            "\n" +
                            "\n" +
                            "        Cursor cursor = db.query(\n" +
                            "                Contract.Entry.TABLE_NAME,\n" +
                            "                projection,\n" +
                            "                selection,\n" +
                            "                selectionArgs,\n" +
                            "                null,\n" +
                            "                null,\n" +
                            "                null\n" +
                            "        );\n" +
                            "\n" +
                            "        String items = null;\n" +
                            "        while (cursor.moveToNext()) {\n" +
                            "            items = cursor.getString(\n" +
                            "                    cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_NAME_VALUE));\n" +
                            "\n" +
                            "        }\n" +
                            "        cursor.close();\n" +
                            "\n" +
                            "        Type type = TypeToken.getParameterized(List.class, clazz).getType();\n" +
                            "        List<T> list = new Gson().fromJson(items, type);\n" +
                            "        return list;\n" +
                            "    }\n" +
                            "\n" +
                            "    public void saveMyList(String keyname, List<? extends Serializable> items) {\n" +
                            "\n" +
                            "        ContentValues values = new ContentValues();\n" +
                            "        values.put(Contract.Entry.COLUMN_NAME_KEY, keyname);\n" +
                            "\n" +
                            "        values.put(Contract.Entry.COLUMN_NAME_VALUE,\n" +
                            "                new Gson().toJson(items));\n" +
                            "\n" +
                            "        String where = Contract.Entry.COLUMN_NAME_KEY + \" = ?\";\n" +
                            "\n" +
                            "        String[] whereArgs = {keyname};\n" +
                            "        if (db.update(Contract.Entry.TABLE_NAME,\n" +
                            "                values,\n" +
                            "                where,\n" +
                            "                whereArgs) < 1) {\n" +
                            "\n" +
                            "            db.insert(Contract.Entry.TABLE_NAME,\n" +
                            "                    null,\n" +
                            "                    values);\n" +
                            "        }\n" +
                            "    }\n" +
                            "\n" +
                            "    private final class Contract {\n" +
                            "        private Contract() {\n" +
                            "        }\n" +
                            "\n" +
                            "\n" +
                            "        private class Entry implements BaseColumns {\n" +
                            "            private static final String TABLE_NAME = \"EscapeSQLBoiler\";\n" +
                            "            private static final String COLUMN_NAME_KEY = \"keyname\";\n" +
                            "            private static final String COLUMN_NAME_VALUE = \"value\";\n" +
                            "        }\n" +
                            "    }\n" +
                            "\n" +
                            "    private class SaveListDbHelper extends SQLiteOpenHelper {\n" +
                            "\n" +
                            "        private static final int DATABASE_VERSION = 1;\n" +
                            "        private static final String DATABASE_NAME = \"EscapeSQLBoiler.db\";\n" +
                            "\n" +
                            "        private SaveListDbHelper(Context context) {\n" +
                            "            super(context, DATABASE_NAME, null, DATABASE_VERSION);\n" +
                            "        }\n" +
                            "\n" +
                            "        public void onCreate(SQLiteDatabase db) {\n" +
                            "            db.execSQL(SQL_CREATE_ENTRIES);\n" +
                            "        }\n" +
                            "\n" +
                            "        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {\n" +
                            "\n" +
                            "            db.execSQL(SQL_DELETE_ENTRIES);\n" +
                            "            onCreate(db);\n" +
                            "        }\n" +
                            "\n" +
                            "        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {\n" +
                            "            onUpgrade(db, oldVersion, newVersion);\n" +
                            "        }\n" +
                            "    }\n" +
                            "\n" +
                            "}");

            JavaFileObject javaFileObject = filer.createSourceFile("com.bipinayetra.savelist.EscapeSQLBoiler");

            // Write contents in builder into file
            Writer writer = javaFileObject.openWriter();
            writer.write(stringBuilder.toString());
            writer.close();

        } catch (Exception e) {
            System.out.println("Error " + e.getStackTrace());
        }
        return true;
    }

    private void writeMapofKeyandType(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment, StringBuilder stringBuilder) {
        System.out.println("annotations-->"+annotations);
        Collection<? extends Element> annotatedElements = roundEnvironment
                .getElementsAnnotatedWith(SaveList.class);

        System.out.println("getElementsAnnotatedWith(SaveList.class) --> " + annotatedElements);

        Collection<VariableElement> fields = ElementFilter.fieldsIn(annotatedElements);
        for (VariableElement field : fields) {
            TypeMirror fieldType = field.asType();
            DeclaredType declaredFieldType = (DeclaredType) fieldType;
            List<? extends TypeMirror> getTypeArguments = declaredFieldType.getTypeArguments();

            for (AnnotationMirror annotationMirror : field.getAnnotationMirrors()) {
                Map<ExecutableElement, AnnotationValue> elementValues = new HashMap<ExecutableElement, AnnotationValue>(
                        annotationMirror.getElementValues());
                for (Map.Entry<ExecutableElement, AnnotationValue> entry : elementValues
                        .entrySet()) {
                    stringBuilder.append("mapOfListType.put(" + entry.getValue()+","+getTypeArguments+".class);");
                }
            }
        }
    }
}
