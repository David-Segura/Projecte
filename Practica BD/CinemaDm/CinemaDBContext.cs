using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace CinemaDm
{
    class CinemaDBContext : DbContext
    {
        protected override void OnConfiguring(
            DbContextOptionsBuilder optionBuilder)
        {
            optionBuilder.UseMySQL("Server=localhost;Database=restaurant;UID=root;Password=");
        }
    }
}
