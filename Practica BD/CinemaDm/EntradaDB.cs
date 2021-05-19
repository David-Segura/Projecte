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
    public class EntradaDB
    {

        public static ObservableCollection<Entrada> GetLlistaEntrades()
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
                            consulta.CommandText = $@"  select * from entrada en 
                                                        join funcio f on f.fun_esp_id=en.ent_fun_esp_id 
                                                        join espectacle e on e.esp_id=en.ent_fun_esp_id 
                                                        order by ent_cad_num";
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Entrada> entrades= new ObservableCollection<Entrada>();
                            while (reader.Read())
                            {
                                Entrada c = new Entrada();
                                DBUtils.Llegeix(reader, out c.ent_id, "ent_id");
                                DBUtils.Llegeix(reader, out c.ent_fun_esp_id, "ent_fun_esp_id");
                                DBUtils.Llegeix(reader, out c.ent_fun_num, "ent_fun_num");
                                DBUtils.Llegeix(reader, out c.ent_cad_sal_id, "ent_cad_sal_id");
                                DBUtils.Llegeix(reader, out c.ent_cad_num, "ent_cad_num");
                                DBUtils.Llegeix(reader, out c.ent_preu, "ent_preu",0);
                                entrades.Add(c);
                            }
                            return entrades;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<EntradaDB>();
                log.Fatal("error durant la select de les categories de les cadires");
                return new ObservableCollection<Entrada>();
            }

        }

        public static int GetLocalitats()
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
                            consulta.CommandText = $@"  SELECT count(*)
                                                        from sala s join espectacle e on e.esp_sal_id=s.sal_id 
                                                        join funcio f on f.fun_esp_id=e.esp_id 
                                                        JOIN entrada en on en.ent_fun_num=e.esp_id
                                                        group by s.sal_nom,e.esp_nom,e.esp_desc,f.fun_data
                                                        ";
                            
                            int cant = Convert.ToInt32(consulta.ExecuteScalar());
                            return cant;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<EntradaDB>();
                log.Fatal("error durant la select de les categories de les cadires");
                return 0;
            }

        }


        public static bool Delete(Entrada entrada)
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
                                    $@"select count(1) from entrada where ent_id=@id";
                                DBUtils.createParameter(consulta, "id", entrada.ent_id, DbType.String);
                                object o = consulta.ExecuteScalar();


                                //string cognom, int salari, int deptNo
                                consulta.CommandText =
                                $@"delete from entrada where ent_id=@id";

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

                                    log.Fatal("error durant l'eliminació de l'entrada , filesModificades=" + filesModificades);

                                    

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
    }
}
