using GestioRestaurantDm;
using Microsoft.Toolkit.Uwp.UI.Controls;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=402352&clcid=0xc0a

namespace GestioComandes
{
    /// <summary>
    /// Página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        public MainPage()
        {
            this.InitializeComponent();
            dtgComandes.ItemsSource = llcomandes;
            //dtgComandes.RowDetailsTemplate = 
            createDataGrids();
        }

        ObservableCollection<Comanda> llcomandes = ComandaDB.getLlistaComandesAmbLinies();
        private void DtgComandes_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }

        
        private void createDataGrids()
        {
            int i = 1;
            foreach (Comanda c in llcomandes)
            {
               
                DataGrid dg = new DataGrid();
                ObservableCollection<Linea_Comanda> lllcomanda = Linea_ComandaDB.getLlistaLineaComanda(c.Codi);
                dg.ItemsSource = lllcomanda;

                Binding b = new Binding();
                b.Source = lllcomanda;
                b.Path = new PropertyPath(lllcomanda[0].Comanda.Codi+"");
               
                b.Mode = BindingMode.OneWay;
                dg.SetBinding(ItemsControl.ItemsSourceProperty, b);


                //dg.SetBinding(ItemsControl.ItemsSourceProperty,b);
                Grid.RowDefinitions.Add(new RowDefinition());
                Grid.Children.Add(dg);
                Grid.SetRow(dg, i);
                
                i++;
            } 
        }
    }
}
