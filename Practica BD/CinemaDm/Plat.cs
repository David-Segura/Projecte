using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace GestioRestaurantDm
{
    public class Plat
    {
        internal int codi;
        internal string nom;
        internal string descripcioMD;
        internal float preu;
        internal byte[] foto;
        internal bool disponible;
        internal int categoria;

        public int Codi { get => codi; set => codi = value; }
        public string Nom { get => nom; set => nom = value; }
        public string DescripcioMD { get => descripcioMD; set => descripcioMD = value; }
        public float Preu { get => preu; set => preu = value; }
        public byte[] Foto { get => foto; set => foto = value; }
        public bool Disponible { get => disponible; set => disponible = value; }
        public int Categoria { get => categoria; set => categoria = value; }

        public Plat() { }

        public Plat(int codi, string nom, string descripcioMD, float preu, byte[] foto, bool disponible, int categoria)
        {
            this.codi = codi;
            this.nom = nom;
            this.descripcioMD = descripcioMD;
            this.preu = preu;
            this.foto = foto;
            this.disponible = disponible;
            this.categoria = categoria;
        }
    }    
}



