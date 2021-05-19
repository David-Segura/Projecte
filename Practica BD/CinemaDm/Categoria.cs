using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace GestioRestaurantDm
{
    public class Categoria
    {
        public int id;
        public string nom;
        public int color;
       



        public Categoria(int id, string nom, int color)
        {
            Id = id;
            Nom = nom;
            Color = color;
            
        }
        public Categoria()
        {

        }


        public int Id { get => id; set => id = value; }
        public string Nom { get => nom; set => nom = value; }
        public int Color { get => color; set => color = value; }
        
    }

    
        
}