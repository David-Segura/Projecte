using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioRestaurantDm
{
    public class Comanda
    {





        internal int codi;
        internal DateTime data;
        internal int taula;
        internal Cambrer cambrer = new Cambrer();

        public int Codi { get => codi; set => codi = value; }
        public DateTime Data { get => data; set => data = value; }
        public int Taula { get => taula; set => taula = value; }
        public Cambrer Cambrer { get => cambrer; set => cambrer = value; }

        public Comanda()
        { }

        public Comanda(int codi, DateTime data, int taula, Cambrer cambrer)
        {
            this.codi = codi;
            this.data = data;
            this.taula = taula;
            this.cambrer = cambrer;
        }
    }
}
