using System;
using System.Collections.Generic;
using System.Text;

namespace GestioRestaurantDm
{
    public class Linea_Comanda
    {


        internal Comanda comanda = new Comanda();
        internal int comandaId;
        internal int num;
        internal int quantitat;
        internal int item;
        internal int estat;

        public Comanda Comanda { get => comanda; set => comanda = value; }
        public int ComandaId { get => comandaId; set => comandaId = value; }
        public int Num { get => num; set => num = value; }
        public int Quantitat { get => quantitat; set => quantitat = value; }
        public int Item { get => item; set => item = value; }
        public int Estat { get => estat; set => estat = value; }

        public Linea_Comanda()
        {

        }

        public Linea_Comanda(Comanda comanda, int num, int quantitat, int item, int estat)
        {
            this.comanda = comanda;
            comandaId = comanda.codi;
            this.num = num;
            this.quantitat = quantitat;
            this.item = item;
            this.estat = estat;
        }
    }
}
