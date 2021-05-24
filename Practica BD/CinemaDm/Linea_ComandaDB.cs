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
                                DBUtils.Llegeix(reader, out c.estat, "estat");
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

        


        public static bool Update(
            int id,
            string nom,
            DateTime data_inici,
            DateTime data_fi,
            int sal_id,
            int cae_id,
            string desc
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
                                $@"select count(1) from espectacle where esp_id=@id";
                            DBUtils.createParameter(consulta, "id", id, DbType.Int32);
                            object o = consulta.ExecuteScalar();
                            int numEspectacles = (int)((long)o);
                            if (numEspectacles == 0)
                            {
                                // L'espectacle ja existeix, i al avisar a l'usuari.

                               
                                return false;
                            }
                            else
                            {

                                //string cognom, int salari, int deptNo
                                

                                
                                DBUtils.createParameter(consulta, "nom", nom, DbType.String);
                                DBUtils.createParameter(consulta, "data_inici", data_inici, DbType.DateTime);
                                DBUtils.createParameter(consulta, "data_fi", data_fi, DbType.DateTime);
                                DBUtils.createParameter(consulta, "sal_id", sal_id, DbType.Int32);
                                DBUtils.createParameter(consulta, "cae_id", cae_id, DbType.Int32);
                                DBUtils.createParameter(consulta, "esp_desc", desc, DbType.String);

                                consulta.CommandText =
                                $@"UPDATE espectacle SET esp_nom = [@nom], esp_data_inici = [@data_inici] , esp_data_fi = [@data_fi], esp_sal_id=[@sal_id], esp_cae_id=[@cae_id],esp_desc=[@esp_desc]
                                WHERE esp_id = @id";


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

                                    log.Fatal("error durant la modificació de l'espectacle , filesModificades=" +numEspectacles);

                                    //-----------------------------------------------------
                                    // El log es troba a la carpeta següent
                                    // (el número llarg en hexadecimal és el Package name
                                    // que està a l'arxiu "Package.appmanifest"
                                    // en aquest cas és 727b014c-873f-493e-b051-4dd21cf18dae_n82rqfc3nm07y
                                    //C:\Users\Usuari\AppData\Local\Packages\727b014c-873f-493e-b051-4dd21cf18dae_n82rqfc3nm07y\LocalState\MetroLogs
                                    //-----------------------------------------------------

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
