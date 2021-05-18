using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CinemaDm
{
    public class Cadira: INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        internal int sala;
        internal int id;
        internal int x;
        internal int y;
        internal int cat;
        internal string desc;
        internal EnumEstat estat;

        public Cadira(int sala,int id, int x,int y, int cat, string desc,  EnumEstat estat)
        {
            Sala = sala;
            Id = id;
            X = x;
            Y = y;
            Cat = cat;
            Desc = desc;
            Estat = estat;
        }
        public Cadira()
        { }

        public int Sala { get => sala; set => sala = value; }
        public int Id { get => id; set => id = value; }
        public int X { get => x; set => x = value; }
        public int Y { get => y; set => y = value; }
        public int Cat { get => cat; set => cat = value; }
        public string Desc { get => desc; set => desc = value; }
        public EnumEstat Estat
        {
            get => estat;
            set
            {
                estat = value;
                PropertyChanged?.Invoke(this, 
                    new PropertyChangedEventArgs("Estat"));
            }
        }

    }
}
