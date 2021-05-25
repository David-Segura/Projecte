using MetroLog;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Text;

namespace GestioRestaurantDm
{
    public class IngredientDB
    {

        public static Ingredient getIngredient(int codi)
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
                            consulta.CommandText = $@"  select * from ingredient  where codi = @codi";
                            DBUtils.createParameter(consulta, "codi", codi, DbType.Int32);
                            var reader = consulta.ExecuteReader();
                            Ingredient ingredient = new Ingredient();
                            while (reader.Read())
                            {
                                
                                DBUtils.Llegeix(reader, out ingredient.codi, "codi");
                                DBUtils.Llegeix(reader, out ingredient.nom, "nom");

                                
                            }
                            return ingredient;
                        }
                    }
                }
            }
            catch
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<Ingredient>();
                log.Fatal("error durant la select dels ingredients");
                return new Ingredient();
            }



        }
        public static ObservableCollection<Ingredient> getLlistaIngredients()
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
                            consulta.CommandText = $@"  select * from ingredient  ";
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Ingredient> ingredients = new ObservableCollection<Ingredient>();
                            while (reader.Read())
                            {
                                Ingredient i = new Ingredient();
                                DBUtils.Llegeix(reader, out i.codi, "codi");
                                DBUtils.Llegeix(reader, out i.nom, "nom");

                                ingredients.Add(i);
                            }
                            return ingredients;
                        }
                    }
                }
            }
            catch
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<Ingredient>();
                log.Fatal("error durant la select dels ingredients");
                return new ObservableCollection<Ingredient>();
            }

        }
    }
}
