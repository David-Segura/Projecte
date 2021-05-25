using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;

namespace GestioRestaurantDm
{
    public class Linea_Comanda : INotifyPropertyChanged
    {


        internal Comanda comanda = new Comanda();
        internal int comandaId;
        internal int num;
        internal int quantitat;
        internal dynamic item;
        internal bool acabat;

        public event PropertyChangedEventHandler PropertyChanged;

        public Comanda Comanda { get => comanda; set => comanda = value; }
        public int ComandaId { get => comandaId; set => comandaId = value; }
        public int Num { get => num; set => num = value; }
        public int Quantitat { get => quantitat; set => quantitat = value; }
        public dynamic Item { get => item; set => item = value; }
        public bool Acabat { get => acabat; set => acabat = value; }

        public Linea_Comanda()
        {

        }

        public Linea_Comanda(Comanda comanda, int num, int quantitat, dynamic item, bool acabat)
        {
            this.comanda = comanda;
            comandaId = comanda.codi;
            this.num = num;
            this.quantitat = quantitat;
            this.item = item;
            this.acabat = acabat;
        }
    }
}
