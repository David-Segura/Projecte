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
    public class EspectacleDB
    {
        public static ObservableCollection<Espectacle> getLlistaEspectacles()
        {
            return getLlistaEspectacles("", -1);
        }

        public static ObservableCollection<Espectacle> getLlistaEspectacles(string nom_desc,int numCat)
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
                            consulta.CommandText = $@"  select e.* from espectacle e
                                                        join catespectacle c on e.esp_cae_id = c.cae_id
                                                        where (esp_nom like @nom_desc
                                                        OR esp_desc like @nom_desc)
                                                        and (@numCat=-1 OR esp_cae_id=@numCat)";

                            DBUtils.createParameter(consulta, "nom_desc", $"%{nom_desc}%", DbType.String);
                            DBUtils.createParameter(consulta, "numCat", numCat, DbType.Int32);

                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Espectacle> espectacles = new ObservableCollection<Espectacle>();
                            while (reader.Read())
                            {
                                Espectacle c = new Espectacle();
                                DBUtils.Llegeix(reader, out c.esp_id, "esp_id");
                                DBUtils.Llegeix(reader, out c.esp_nom, "esp_nom");
                                DBUtils.Llegeix(reader, out c.esp_data_inici, "esp_data_inici");
                                DBUtils.Llegeix(reader, out c.esp_data_fi, "esp_data_fi",DateTime.Today);
                                DBUtils.Llegeix(reader, out c.esp_sal_id, "esp_sal_id");
                                DBUtils.Llegeix(reader, out c.esp_cae_id, "esp_cae_id");
                                DBUtils.Llegeix(reader, out c.esp_desc, "esp_desc","");
                                espectacles.Add(c);
                            }
                            return espectacles;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<EspectacleDB>();
                log.Fatal("error durant la select dels espectacles");
                return new ObservableCollection<Espectacle>();
            }

        }

        public static Espectacle getEspectacle(int e_id)
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
                            consulta.CommandText = $@"  select e.* from espectacle e
                                                        where esp_id=@e_id";

                           
                            DBUtils.createParameter(consulta, "e_id", e_id, DbType.Int32);

                            var reader = consulta.ExecuteReader();

                            reader.Read();
                                Espectacle c = new Espectacle();
                                DBUtils.Llegeix(reader, out c.esp_id, "esp_id");
                                DBUtils.Llegeix(reader, out c.esp_nom, "esp_nom");
                                DBUtils.Llegeix(reader, out c.esp_data_inici, "esp_data_inici");
                                DBUtils.Llegeix(reader, out c.esp_data_fi, "esp_data_fi", DateTime.Today);
                                DBUtils.Llegeix(reader, out c.esp_sal_id, "esp_sal_id");
                                DBUtils.Llegeix(reader, out c.esp_cae_id, "esp_cae_id");
                                DBUtils.Llegeix(reader, out c.esp_desc, "esp_desc", "");
                               
                           
                            return c;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<EspectacleDB>();
                log.Fatal("error durant la select dels espectacles");
                return new Espectacle();
            }


        }


        public static bool Delete(Espectacle espectacle)
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
                            DbTransaction transaction = null;
                            try
                            {

                                // Creem transacció
                                transaction = connexio.BeginTransaction();
                                consulta.Transaction = transaction; // Ara si que la consulta usa la transacció

                                // 
                                consulta.CommandText =
                                    $@"select count(1) from espectacle where esp_id=@id";
                                DBUtils.createParameter(consulta, "id", espectacle.esp_id, DbType.Int32);
                                object o = consulta.ExecuteScalar();
                                

                                    //string cognom, int salari, int deptNo
                                    consulta.CommandText =
                                    $@"delete from espectacle where esp_id=@id";

                                    int filesModificades = (int)consulta.ExecuteNonQuery();
                                    if (filesModificades == 1)
                                    {
                                        //transaction.Commit();
                                       
                                        return true;
                                    }
                                    else
                                    {
                                        // OMG!
                                        // rollback !!!!!!!!
                                        transaction.Rollback();

                                        ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<EspectacleDB>();

                                        log.Fatal("error durant l'eliminació de l'espectacle , filesModificades=" + filesModificades);

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
                            catch (Exception e)
                            {
                                if (transaction != null)
                                    transaction.Rollback();
                               
                                return false;
                            }

                        }
                    }
                }
            }
            catch (Exception ex)
            {
               
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<EspectacleDB>();
                log.Error("Error inesperat a l'actualització de dades", ex);
                return false;
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

                                    ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<EspectacleDB>();

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
                
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<EspectacleDB>();
                log.Error("Error inesperat a l'actualització de dades", ex);
                return false;
            }
        }
    }
}
