using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace GestioRestaurantDm
{
    class RestaurantDBContext : DbContext
    {
        protected override void OnConfiguring(
            DbContextOptionsBuilder optionBuilder)
        {
            optionBuilder.UseMySQL("Server=51.68.224.27;Database=dam2_dsegura;UID=dam2-dsegura;Password=47120851S");
        }
    }
}
