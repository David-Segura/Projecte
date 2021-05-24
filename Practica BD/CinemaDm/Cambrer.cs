using System;
using System.Collections.Generic;
using System.Text;

namespace GestioRestaurantDm
{
    public class Cambrer
    {



        internal int codi;
        internal string nom;
        internal string cognom1;
        internal string cognom2;
        internal string user;
        internal string password;

        public int Codi { get => codi; set => codi = value; }
        public string Nom { get => nom; set => nom = value; }
        public string Cognom1 { get => cognom1; set => cognom1 = value; }
        public string Cognom2 { get => cognom2; set => cognom2 = value; }
        public string User { get => user; set => user = value; }
        public string Password { get => password; set => password = value; }

        public Cambrer() { }

        public Cambrer(int codi, string nom, string cognom1, string cognom2, string user, string password)
        {
            this.codi = codi;
            this.nom = nom;
            this.cognom1 = cognom1;
            this.cognom2 = cognom2;
            this.user = user;
            this.password = password;
        }
    }
}
