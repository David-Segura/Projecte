using System;
using System.Collections.Generic;
using System.Text;

namespace GestioRestaurantDm
{
    public class Ingredient
    {
        internal int sal_id;
        internal int cat_num;
        internal int esp_id;
        internal int preu;

        public int Sal_id { get => sal_id; set => sal_id = value; }
        public int Cat_num { get => cat_num; set => cat_num = value; }
        public int Esp_id { get => esp_id; set => esp_id = value; }
        public int Preu { get => preu; set => preu = value; }

        public Ingredient(int sal_id, int cat_num, int esp_id, int preu)
        {
            this.sal_id = sal_id;
            this.cat_num = cat_num;
            this.esp_id = esp_id;
            this.preu = preu;
        }
        public Ingredient()
        {

        }
    }
}
