
using MetroLog;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

namespace CinemaDm
{
    public class CadiraDB
    {
        public static ObservableCollection<Cadira> getLlistaCadires()
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
                            consulta.CommandText = $@"  select * from cadira  ";
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Cadira> cadires = new ObservableCollection<Cadira>();
                            while (reader.Read())
                            {
                              
                                Cadira c = new Cadira();
                                DBUtils.Llegeix(reader, out c.sala, "cad_sal_id");
                                DBUtils.Llegeix(reader, out c.id, "cad_num");
                                DBUtils.Llegeix(reader, out c.x, "cad_x");
                                DBUtils.Llegeix(reader, out c.y, "cad_y");
                                DBUtils.Llegeix(reader, out c.cat, "cad_cat_num");
                                DBUtils.Llegeix(reader, out c.desc, "cad_desc","");
                                
                                cadires.Add(c);
                            }
                            return cadires;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<CadiraDB>();
                log.Fatal("error durant la select de les cadires");
                return new ObservableCollection<Cadira>();
            }

        }

        public static ObservableCollection<Cadira> getLlistaCadiresCompra()
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
                            consulta.CommandText = $@"  select ca.cad_num,c.cat_nom 
                                                        from cadira ca join cadcategoria c on cad_cat_num = cat_num
                                                        ;";
                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Cadira> cadires = new ObservableCollection<Cadira>();
                            while (reader.Read())
                            {

                                Cadira c = new Cadira();
                                
                                DBUtils.Llegeix(reader, out c.id, "cad_num");
                                DBUtils.Llegeix(reader, out c.desc, "cat_nom");
                               

                                cadires.Add(c);
                            }
                            return cadires;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<CadiraDB>();
                log.Fatal("error durant la select de les cadires");
                return new ObservableCollection<Cadira>();
            }

        }




    }
}
