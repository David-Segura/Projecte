
using MetroLog;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

namespace GestioRestaurantDm
{
    public class ComandaDB
    {
        public static ObservableCollection<Comanda> getLlistaComandes()
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
                            consulta.CommandText = $@"  select co.*, ca.nom from comanda co join cambrer ca on co.cambrer = ca.codi";
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Comanda> comandes = new ObservableCollection<Comanda>();
                            while (reader.Read())
                            {
                              
                                Comanda c = new Comanda();
                                DBUtils.Llegeix(reader, out c.codi, "codi");
                                DBUtils.Llegeix(reader, out c.data, "data");
                                DBUtils.Llegeix(reader, out c.taula, "taula");
                                DBUtils.Llegeix(reader, out c.cambrer.nom, "nom");
                                
                                
                                comandes.Add(c);
                            }
                            return comandes;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<ComandaDB>();
                log.Fatal("error durant la select de les comandes");
                return new ObservableCollection<Comanda>();
            }

        }

        public static ObservableCollection<Comanda> getLlistaComandesAmbLinies()
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
                            consulta.CommandText = $@"  select co.*, ca.nom from comanda co join cambrer ca on co.cambrer = ca.codi
                                                        where co.codi in (select comanda from linea_comanda where acabat = false)";
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Comanda> comandes = new ObservableCollection<Comanda>();
                            while (reader.Read())
                            {
                              
                                Comanda c = new Comanda();
                                DBUtils.Llegeix(reader, out c.codi, "codi");
                                DBUtils.Llegeix(reader, out c.data, "data");
                                DBUtils.Llegeix(reader, out c.taula, "taula");
                                DBUtils.Llegeix(reader, out c.cambrer.nom, "nom");
                                
                                
                                comandes.Add(c);
                            }
                            return comandes;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<ComandaDB>();
                log.Fatal("error durant la select de les comandes");
                return new ObservableCollection<Comanda>();
            }

        }

       




    }
}
