using CinemaDM;
using MetroLog;
using Microsoft.EntityFrameworkCore;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Globalization;
using System.Text;

namespace CinemaDm
{
    public class PlatDB
    {

        public static ObservableCollection<Plat> getLlistaPlats()
        {
            return getLlistaPlats(-1,"");
        }

        public static ObservableCollection<Plat> getLlistaPlats(int numCat, string nomPlat)
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
                            consulta.CommandText = $@" select * from plat where (@numCat=-1 OR categoria=@numCat) and nom like @nomPlat";
                            DBUtils.createParameter(consulta, "numCat", numCat, DbType.Int32);
                            DBUtils.createParameter(consulta, "nomPlat", $"%{nomPlat}%", DbType.String);

                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Plat> sales = new ObservableCollection<Plat>();
                            while (reader.Read())
                            {
                                Plat c = new Plat();
                                DBUtils.Llegeix(reader, out c.codi, "codi");
                                DBUtils.Llegeix(reader, out c.nom, "nom");
                                DBUtils.Llegeix(reader, out c.descripcioMD, "descripcioMD");
                                DBUtils.Llegeix(reader, out c.preu, "preu");
                                DBUtils.Llegeix(reader, out c.foto, "foto");
                                DBUtils.Llegeix(reader, out c.disponible, "disponible");
                                DBUtils.Llegeix(reader, out c.categoria, "categoria");
                                sales.Add(c);
                            }
                            return sales;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<PlatDB>();
                log.Fatal("error durant la select dels plats");
                return new ObservableCollection<Plat>();
            }

        }
        public static int getMaxCodi()
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
                            consulta.CommandText = $@" select max(codi) as codi from plat";
                            

                            var reader = consulta.ExecuteReader();
                            int maxCodi = 0;
                            while (reader.Read())
                            {
                                DBUtils.Llegeix(reader, out maxCodi, "codi");
                               
                            }
                            
                            return maxCodi;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<PlatDB>();
                log.Fatal("error durant la select dels plats");
                return 0;
            }

        }

        public static bool Delete(Plat plat)
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
                            DbTransaction transaction = null;
                            try
                            {

                                // Creem transacció
                                transaction = connexio.BeginTransaction();
                                consulta.Transaction = transaction; // Ara si que la consulta usa la transacció

                                // 
                                consulta.CommandText =
                                   $@"delete from plat where codi=@id";
                                DBUtils.createParameter(consulta, "id", plat.codi, DbType.Int32);
                                //object o = consulta.ExecuteScalar();


                                //string cognom, int salari, int deptNo
                                //consulta.CommandText =
                                //$@"delete from plat where codi=@id";
                                //DBUtils.createParameter(consulta, "id", plat.codi, DbType.Int32);
                                int filesModificades =1;//= (int)consulta.ExecuteNonQuery();
                                consulta.ExecuteNonQuery();
                                if (filesModificades == 1)
                                {
                                    transaction.Commit();

                                    return true;
                                }
                                else
                                {
                                    // OMG!
                                    // rollback !!!!!!!!
                                    transaction.Rollback();

                                    ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<PlatDB>();

                                    log.Fatal("error durant l'eliminació del plat , filesModificades=" + filesModificades);



                                    return false;

                                }


                            }
                            catch (Exception e)
                            {
                                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<PlatDB>();

                                log.Fatal("error durant l'eliminació del plat: plat amb referencies");

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

                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<PlatDB>();
                log.Error("Error inesperat a l'actualització de dades", ex);
                return false;
            }

        }


        public static bool Insert(Plat plat)
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
                            DbTransaction transaction = null;
                            try
                            {

                                // Creem transacció
                                transaction = connexio.BeginTransaction();
                                consulta.Transaction = transaction; // Ara si que la consulta usa la transacció

                                // 
                                DBUtils.createParameter(consulta, "codi", plat.codi, DbType.Int32);
                                DBUtils.createParameter(consulta, "nom", plat.nom, DbType.String);
                                DBUtils.createParameter(consulta, "desc", plat.descripcioMD, DbType.String);
                                DBUtils.createParameter(consulta, "preu", plat.preu, DbType.Double);
                                DBUtils.createParameter(consulta, "foto", plat.foto, DbType.Binary);
                                DBUtils.createParameter(consulta, "disponible", plat.disponible, DbType.Boolean);
                                DBUtils.createParameter(consulta, "categoria", plat.categoria, DbType.Int32);
                                NumberFormatInfo nfi = new NumberFormatInfo();
                                nfi.NumberDecimalSeparator = ".";
                                //object o = consulta.ExecuteScalar();
                                consulta.CommandText =
                                   $@"insert into plat values("+plat.codi+ ",'" + plat.nom + "','" + plat.descripcioMD + "',"+plat.preu.ToString(nfi)+", @foto,"+plat.disponible + "," + plat.categoria + ")";


                                //string cognom, int salari, int deptNo
                                //consulta.CommandText =
                                //$@"delete from plat where codi=@id";
                                //DBUtils.createParameter(consulta, "id", plat.codi, DbType.Int32);
                                int filesModificades = 1;//= (int)consulta.ExecuteNonQuery();
                                consulta.ExecuteNonQuery();
                                if (filesModificades == 1)
                                {
                                    transaction.Commit();

                                    return true;
                                }
                                else
                                {
                                    // OMG!
                                    // rollback !!!!!!!!
                                    transaction.Rollback();

                                    ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<PlatDB>();

                                    log.Fatal("error durant l'eliminació del plat , filesModificades=" + filesModificades);



                                    return false;

                                }


                            }
                            catch (Exception e)
                            {
                                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<PlatDB>();

                                log.Fatal("error durant l'eliminació del plat: plat amb referencies");

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

                ILogger log = LogManagerFactory.DefaultLogManager.GetLogger<PlatDB>();
                log.Error("Error inesperat a l'actualització de dades", ex);
                return false;
            }

        }
    }
}
