using MetroLog;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Text;

namespace CinemaDm
{
    public class FuncioDB
    {
        public static ObservableCollection<Funcio> getLlistaFuncions(int esp_id)
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
                            consulta.CommandText = $@"  select * from funcio where fun_esp_id = @id ";
                            DBUtils.createParameter(consulta, "id", esp_id, DbType.Int32);
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Funcio> funcions = new ObservableCollection<Funcio>();
                            while (reader.Read())
                            {

                                Funcio c = new Funcio();
                                DBUtils.Llegeix(reader, out c.id, "fun_esp_id");
                                DBUtils.Llegeix(reader, out c.num, "fun_num");
                                DBUtils.Llegeix(reader, out c.data, "fun_data");
                                

                                funcions.Add(c);
                            }
                            return funcions;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<FuncioDB>();
                log.Fatal("error durant la select de les funcions");
                return new ObservableCollection<Funcio>();
            }

        }
    }
}
