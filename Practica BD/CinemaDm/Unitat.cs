using System;
using System.Collections.Generic;
using System.Text;

namespace CinemaDm
{
    public class Unitat
    {
        internal int codi;
        internal string nom;

        public int Codi { get => codi; set => codi = value; }
        public string Nom { get => nom; set => nom = value; }

        public Unitat(int codi, string nom)
        {
            this.Codi = codi;
            this.Nom = nom;
        }
        public Unitat() { }
    }
}
