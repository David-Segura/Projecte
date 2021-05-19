using MetroLog;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

namespace GestioRestaurantDm
{
    public class IngredientDB
    {
        public static ObservableCollection<Ingredient> getLlistaPreus()
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
                            consulta.CommandText = $@"  select * from preucatespec  ";
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Ingredient> preus = new ObservableCollection<Ingredient>();
                            while (reader.Read())
                            {
                                Ingredient c = new Ingredient();
                                DBUtils.Llegeix(reader, out c.sal_id, "ptc_cat_sal_id");
                                DBUtils.Llegeix(reader, out c.cat_num, "ptc_cat_num");
                                DBUtils.Llegeix(reader, out c.esp_id, "ptc_esp_id");
                                DBUtils.Llegeix(reader, out c.preu, "pct_preu");
                                preus.Add(c);
                            }
                            return preus;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<Ingredient>();
                log.Fatal("error durant la select dels preus");
                return new ObservableCollection<Ingredient>();
            }

        }
    }
}
