using System;
using System.Collections.Generic;
using System.Text;

namespace GestioRestaurantDm
{
    public class Entrada
    {
        internal string ent_id;
        internal int ent_fun_esp_id;
        internal string ent_fun_num;
        internal int ent_cad_sal_id;
        internal int ent_cad_num;
        internal decimal ent_preu;

        public string Ent_id { get => ent_id; set => ent_id = value; }
        public int Ent_fun_esp_id { get => ent_fun_esp_id; set => ent_fun_esp_id = value; }
        public string Ent_fun_num { get => ent_fun_num; set => ent_fun_num = value; }
        public int Ent_cad_sal_id { get => ent_cad_sal_id; set => ent_cad_sal_id = value; }
        public int Ent_cad_num { get => ent_cad_num; set => ent_cad_num = value; }
        public decimal Ent_preu { get => ent_preu; set => ent_preu = value; }
        public Entrada() { }

        public Entrada(string ent_id, int ent_fun_esp_id, string ent_fun_num, int ent_cad_sal_id, int ent_cad_num, decimal ent_preu)
        {
            this.ent_id = ent_id;
            this.ent_fun_esp_id = ent_fun_esp_id;
            this.ent_fun_num = ent_fun_num;
            this.ent_cad_sal_id = ent_cad_sal_id;
            this.ent_cad_num = ent_cad_num;
            this.ent_preu = ent_preu;
        }
    }
}
