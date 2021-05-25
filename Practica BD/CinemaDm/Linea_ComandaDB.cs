using MetroLog;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Text;

namespace GestioRestaurantDm
{
    public class Linea_ComandaDB
    {
        public static ObservableCollection<Linea_Comanda> getLlistaLineaComanda()
        {
            return getLlistaLineaComanda( -1);
        }

        public static ObservableCollection<Linea_Comanda> getLlistaLineaComanda(int idComanda)
        {
            try
            {
               
                using (RestaurantDBContext context = new RestaurantDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText = $@" select * from linea_comanda where comanda = @id or @id = -1";


                            DBUtils.createParameter(consulta, "id", idComanda, DbType.Int32);

                            

                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Linea_Comanda> lcomandes = new ObservableCollection<Linea_Comanda>();
                            while (reader.Read())
                            {
                                Linea_Comanda c = new Linea_Comanda();
                                DBUtils.Llegeix(reader, out c.comanda.codi, "comanda");
                                DBUtils.Llegeix(reader, out c.comandaId, "comanda");
                                DBUtils.Llegeix(reader, out c.num, "num");
                                DBUtils.Llegeix(reader, out c.quantitat, "quantitat");
                                DBUtils.Llegeix(reader, out c.item, "item");
                                DBUtils.Llegeix(reader, out c.acabat, "acabat");
                                lcomandes.Add(c);
                            }
                            return lcomandes;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<Linea_ComandaDB>();
                log.Fatal("error durant la select dels espectacles");
                return new ObservableCollection<Linea_Comanda>();
            }

        }


        /*comanda integer(7),
	num integer(3),
	quantitat integer(3),
	item integer(7),
	acabat boolean,*/

        public static bool Update(
            int comanda,
            int num,
            int quantitat,
            bool acabat
            
            )
        {
           
            try
            {
                using (RestaurantDBContext context = new RestaurantDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            // Creem transacció
                            DbTransaction transaction = connexio.BeginTransaction();
                            consulta.Transaction = transaction; // Ara si que la consulta usa la transacció

                            // 
                            consulta.CommandText =
                                $@"select count(1) from Linea_Comanda where comanda=@id and num = @num";
                            DBUtils.createParameter(consulta, "id", comanda, DbType.Int32);
                            DBUtils.createParameter(consulta, "num", num, DbType.Int32);
                            object o = consulta.ExecuteScalar();
                            int numEspectacles = (int)((long)o);
                            if (numEspectacles == 0)
                            {
                                // L'espectacle ja existeix, i al avisar a l'usuari.

                               
                                return false;
                            }
                            else
                            {

                               
                                

                                
                                DBUtils.createParameter(consulta, "comanda", comanda, DbType.Int32);
                               
                                DBUtils.createParameter(consulta, "quantitat", quantitat, DbType.Int32);
                                DBUtils.createParameter(consulta, "acabat", acabat, DbType.Boolean);
                               

                                consulta.CommandText =
                                $@"UPDATE linea_comanda SET acabat = @acabat WHERE comanda = @comanda and num = @num";

                                object o2 = consulta.ExecuteScalar();
                                if (numEspectacles == 1)
                                {
                                    transaction.Commit();
                                    return true;
                                }
                                else
                                {
                                    // OMG!
                                    // rollback !!!!!!!!
                                    transaction.Rollback();

                                    ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<Linea_ComandaDB>();

                                    log.Fatal("error durant la modificació de la linea comanda , filesModificades=" +numEspectacles);

                                    
                                    return false;

                                }
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<Linea_ComandaDB>();
                log.Error("Error inesperat a l'actualització de dades", ex);
                return false;
            }
        }
    }
}
