using System;
using System.Collections.Generic;
using System.Text;

namespace GestioRestaurantDm
{
    public class Ingredient
    {
        internal int codi;
        internal string nom;
        

        public int Codi { get => codi; set => codi = value; }
        public string Nom { get => nom; set => nom = value; }
       

        public Ingredient(int codi, string nom)
        {
            this.codi = codi;
            this.nom = nom;
            
        }
        public Ingredient()
        {

        }
    }
}
