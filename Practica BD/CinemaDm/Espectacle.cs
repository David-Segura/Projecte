using System;
using System.Collections.Generic;
using System.Text;

namespace CinemaDm
{
    public class Espectacle
    {
        internal int esp_id;
        internal string esp_nom;
        internal DateTime esp_data_inici;
        internal DateTime esp_data_fi;
        internal int esp_sal_id;
        internal int esp_cae_id;
        internal string esp_desc;

        public int Esp_id { get => esp_id; set => esp_id = value; }
        public string Esp_nom { get => esp_nom; set => esp_nom = value; }
        public DateTime Esp_data_inici { get => esp_data_inici; set => esp_data_inici = value; }
        public DateTime Esp_data_fi { get => esp_data_fi; set => esp_data_fi = value; }
        public int Esp_sal_id { get => esp_sal_id; set => esp_sal_id = value; }
        public int Esp_cae_id { get => esp_cae_id; set => esp_cae_id = value; }
        public string Esp_desc { get => esp_desc; set => esp_desc = value; }

        public Espectacle(int esp_id, string esp_nom, DateTime esp_data_inici, DateTime esp_data_fi, int esp_sal_id, int esp_cae_id, string esp_desc)
        {
            this.esp_id         = esp_id;
            this.esp_nom        = esp_nom;
            this.esp_data_inici = esp_data_inici;
            this.esp_data_fi    = esp_data_fi;
            this.esp_sal_id     = esp_sal_id;
            this.esp_cae_id     = esp_cae_id;
            this.esp_desc       = esp_desc;
        }
        public Espectacle()
        {

        }

        public Espectacle(Espectacle source)
        {
            this.esp_id          = source.esp_id;
            this.esp_nom         = source.esp_nom;
            this.esp_data_inici  = source.esp_data_inici;
            this.esp_data_fi     = source.esp_data_fi;
            this.esp_sal_id      = source.esp_sal_id;
            this.esp_cae_id      = source.esp_cae_id;
            this.esp_desc        = source.esp_desc;

        }
    }
}
