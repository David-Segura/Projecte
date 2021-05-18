using CinemaDm;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace _9_Cinema_UserControl.View
{
    public sealed partial class UIManteniment : UserControl
    {
        public UIManteniment()
        {
            this.InitializeComponent();
        }



        public Espectacle UnEspectacle
        {
            get { return (Espectacle)GetValue(UnEspectacleProperty); }
            set { SetValue(UnEspectacleProperty, value); }
        }

        // Using a DependencyProperty as the backing store for UnEspectacle.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty UnEspectacleProperty =
            DependencyProperty.Register("UnEspectacle", typeof(Espectacle), typeof(UIManteniment), new PropertyMetadata(null));

        private void ucManteniment_Loaded(object sender, RoutedEventArgs e)
        {
            cboCategoria.ItemsSource = CategoriaDB.getLlistaCategories();
            cboCategoria.DisplayMemberPath = "Nom";
            // Decidim quin dels camps és el que es fa servir
            // per la propietat "SelectedValue"
            
        }

        private void TxbSala_TextChanged(object sender, TextChangedEventArgs e)
        {
            string nom_sala = txbSala.Text;
            

            lvSales.ItemsSource = null;

            lvSales.ItemsSource = PlatDB.getLlistaPlats();
            lvSales.DisplayMemberPath = "Nom";
        }
    }
}
