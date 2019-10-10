package br.com.sqliteexemplo;

import android.provider.BaseColumns;


// classe final não pode ser instanciada
public final class AlunoContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private AlunoContract() {}

    /* Inner class that defines the table contents */
    public static class AlunoEntry implements BaseColumns {
        public static final String TABLE_NAME = "aluno";
        public static final String COLUMN_NAME_NOME = "nome";

    }
}