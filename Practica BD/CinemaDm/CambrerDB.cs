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
    public class CambrerDB
    {

        public static ObservableCollection<Cambrer> GetLlistaCaambrers()
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
                            consulta.CommandText = $@"  select * from cambrer";
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Cambrer> cambrers= new ObservableCollection<Cambrer>();
                            while (reader.Read())
                            {
                                Cambrer c = new Cambrer();
                                DBUtils.Llegeix(reader, out c.codi, "codi");
                                DBUtils.Llegeix(reader, out c.nom, "nom");
                                DBUtils.Llegeix(reader, out c.cognom1, "cognom1");
                                DBUtils.Llegeix(reader, out c.cognom2, "cognom2");
                                DBUtils.Llegeix(reader, out c.user, "user");
                                DBUtils.Llegeix(reader, out c.password, "password");
                                cambrers.Add(c);
                            }
                            return cambrers;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<CambrerDB>();
                log.Fatal("error durant la select de les categories de les cadires");
                return new ObservableCollection<Cambrer>();
            }

        }

        


        
    }
}
