using MySql.Data.MySqlClient;
using System;
using System.Data;
using System.Data.Common;
using System.IO;

namespace CinemaDm
{
    public class DBUtils
    {
        public static void createParameter(
            DbCommand consulta,
            string nom, object valor, DbType tipus)
        {
            DbParameter param = consulta.CreateParameter();
            param.ParameterName = nom;
            param.Value = valor;
            param.DbType = tipus;
            consulta.Parameters.Add(param);
        }



        // supermagic happy function
        public static void Llegeix(DbDataReader reader, out int valor, string nomColumna, int valorPerDefecte = -1)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));

                valor = reader.GetInt32(ordinal);
            }
        }

        public static void Llegeix(DbDataReader reader, out float valor, string nomColumna, float valorPerDefecte = -1)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));

                valor = reader.GetFloat(ordinal);
            }


        }

        public static void Llegeix(DbDataReader reader, out bool valor, string nomColumna, bool valorPerDefecte = false)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));

                valor = reader.GetBoolean(ordinal);
            }


        }

        public static void Llegeix(DbDataReader reader, out byte valor, string nomColumna, byte valorPerDefecte = 0x00)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));

                valor = reader.GetByte(ordinal);
            }


        }

        public static void Llegeix(DbDataReader reader, out byte[] valor, string nomColumna, byte[] valorPerDefecte = null)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));
                //byte[] buffer = new byte[50000];
                valor = (byte[])reader.GetValue(ordinal);

                //long l = reader.GetBytes(ordinal,0,valor,0,50000);
                //valor = BitConverter.GetBytes(l);
            }


        }

        public static void Llegeix(DbDataReader reader, out string valor, string nomColumna, string valorPerDefecte = "")
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));

                valor = reader.GetString(ordinal);
            }
        }
        public static void Llegeix(DbDataReader reader, out DateTime valor, string nomColumna, DateTime valorPerDefecte = new DateTime())
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));

                valor = reader.GetDateTime(ordinal);
            }
        }


        public static void Llegeix(DbDataReader reader, out Decimal valor, string nomColumna, Decimal valorPerDefecte = 0m)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));

                valor = reader.GetDecimal(ordinal);
            }
        }

        internal static object GetId(DbConnection connexio, DbTransaction transaction, string table_name)
        {
            using (var consulta = connexio.CreateCommand())
            {
                consulta.Transaction = transaction; // Ara si que la consulta usa la transacció

                // 
                consulta.CommandText =
                    $@"select last_id from ids where table_name=@table_name for update";
                DBUtils.createParameter(consulta, "table_name", table_name, DbType.String);
                object o = consulta.ExecuteScalar();
                int last_id = (int)((decimal)o);
                last_id++;
                consulta.CommandText = $@"update ids set last_id=@last_id where table_name=@table_name ";
                //DBUtils.createParameter(consulta, "table_name", table_name, DbType.String);
                DBUtils.createParameter(consulta, "last_id", last_id, DbType.Int32);
                int filesActualitzades = consulta.ExecuteNonQuery();

                if (filesActualitzades != 1)
                {
                    throw new Exception("Error actualitzant IDS");
                }

                return last_id;
            }
        }
    }
}
