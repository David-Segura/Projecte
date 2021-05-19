using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GestioRestaurantDm
{
    public class ItemEscenari
    {
        private string nom;
        private Point coordenadaEsquerraSuperior;
        private Point coordenadaDretaInferior;

        public ItemEscenari(string nom, Point coordenadaEsquerraSuperior, Point coordenadaDretaInferior)
        {
            Nom = nom;
            CoordenadaEsquerraSuperior = coordenadaEsquerraSuperior;
            CoordenadaDretaInferior = coordenadaDretaInferior;
        }

        public string Nom { get => nom; set => nom = value; }
        public Point CoordenadaEsquerraSuperior { get => coordenadaEsquerraSuperior; set => coordenadaEsquerraSuperior = value; }
        public Point CoordenadaDretaInferior { get => coordenadaDretaInferior; set => coordenadaDretaInferior = value; }
    }
}
