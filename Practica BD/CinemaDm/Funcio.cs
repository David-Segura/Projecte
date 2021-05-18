using System;
using System.Collections.Generic;
using System.Text;

namespace CinemaDm
{
    public class Funcio
    {
        internal int id;
        internal int num;
        internal DateTime data;

        public Funcio(int id, int num, DateTime data)
        {
            this.id = id;
            this.num = num;
            this.data = data;
        }
        public Funcio()
        {

        }

       public int Id { get => id; set => id = value; }
       public int Num { get => num; set => num = value; }
       public DateTime Data { get => data; set => data = value; }
    }
}
