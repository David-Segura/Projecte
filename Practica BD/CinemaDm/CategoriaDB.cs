using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

using MetroLog;
using Microsoft.EntityFrameworkCore;

namespace GestioRestaurantDm
{
    public class CategoriaDB
    {
        public static ObservableCollection<Categoria> getLlistaCategories()
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
                            consulta.CommandText = $@"  select * from categoria  ";
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Categoria> categories = new ObservableCollection<Categoria>();
                            while (reader.Read())
                            {
                                Categoria c = new Categoria();
                                DBUtils.Llegeix(reader, out c.id, "codi");
                                DBUtils.Llegeix(reader, out c.nom, "nom");
                                DBUtils.Llegeix(reader, out c.color, "color");
                                categories.Add(c);
                            }
                            return categories;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<CategoriaDB>();
                log.Fatal("error durant la select de les categories");
                return new ObservableCollection<Categoria>();
            }

        }
    }
}
