using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;
using MetroLog;
using Microsoft.EntityFrameworkCore;

namespace CinemaDm
{
    public class UnitatDB
    {
        public static ObservableCollection<Unitat> GetLlistaUnitats()
        {
            try
            {

                using (CinemaDBContext context = new CinemaDBContext())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            consulta.CommandText = $@"  select * from unitat  ";
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Unitat> c_unitats = new ObservableCollection<Unitat>();
                            while (reader.Read())
                            {
                                Unitat c = new Unitat();
                                DBUtils.Llegeix(reader, out c.codi, "codi");
                                DBUtils.Llegeix(reader, out c.nom, "nom");
                                c_unitats.Add(c);
                            }
                            return c_unitats;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<UnitatDB>();
                log.Fatal("error durant la select de les categories de les unitats");
                return new ObservableCollection<Unitat>();
            }

        }
    }
}
