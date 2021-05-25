using GestioRestaurantDm;
using Microsoft.Toolkit.Uwp.UI.Controls;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading;
using System.Threading.Tasks;
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
        Timer _timer;
        public MainPage()
        {
            this.InitializeComponent();
            dtgComandes.ItemsSource = llcomandes;
            //dtgComandes.RowDetailsTemplate = 
            createDataGrids();
            _timer = new System.Threading.Timer(new System.Threading.TimerCallback((obj) => Refresh()), null, 0, 02000);

        }

        private async void Refresh()
        {
            //call your database here...
            //and update the UI afterwards:
            await Windows.ApplicationModel.Core.CoreApplication.MainView.CoreWindow.Dispatcher.RunAsync(Windows.UI.Core.CoreDispatcherPriority.Normal,
            () =>
            {
                // Your UI update code goes here!
                createDataGrids();
            });
        }



        ObservableCollection<Comanda> llcomandes = ComandaDB.getLlistaComandesAmbLinies();
        private void DtgComandes_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }

        
        private void refrescaPantalla()
        {
           
            createDataGrids();
            
        }

        

        

        ObservableCollection<Linea_Comanda> lllcomanda;
        Plat plat;
        private void createDataGrids()
        {
            
            int i = 0;
            int j = 0;
            llcomandes = ComandaDB.getLlistaComandesAmbLinies();
            for (int l = Grid.Children.Count -1 ; l>=0; l--)
            {
                Grid.Children.RemoveAt(l);
            }
            
            foreach (Comanda c in llcomandes)
            {

                DataGrid dg = new DataGrid();
               
                lllcomanda = Linea_ComandaDB.getLlistaLineaComanda(c.Codi);
                dg.ItemsSource = lllcomanda;

                
                if(llcomandes.Count > Grid.ColumnDefinitions.Count)
                {
                    Grid.ColumnDefinitions.Add(new ColumnDefinition());

                }
                Grid.Children.Add(dg);
                
                
                Grid.SetColumn(dg, i);
                Grid.SetRow(dg, j);
                for (int k = 0; k<lllcomanda.Count; k++)
                {
                    plat = PlatDB.getPlatPerCodi(lllcomanda[k].Item);
                    lllcomanda[k].Item = plat.Nom;

                }

                dg.AutoGeneratingColumn += dataGrid_AutoGeneratingColumn;
                dg.CellEditEnded += dg_CellEditEnded;

                
                void dataGrid_AutoGeneratingColumn(object sender, DataGridAutoGeneratingColumnEventArgs e)
                {
                    if (e.PropertyName == "Comanda")
                    {
                        e.Column.Visibility = Visibility.Collapsed;
                    }else if(e.PropertyName == "ComandaId")
                    {
                        e.Column.Header = "Comanda";
                    }else if (e.PropertyName == "Item")
                    {
                        string a = e.PropertyName;
                        
                    }else if (e.PropertyName == "Acabat")
                    {
                        Binding b = new Binding();
                        b.Mode = BindingMode.TwoWay;
                        b.Source = lllcomanda[0].Acabat;

                        // Attach the binding to the target.
                        
                        dg.SetBinding(DataGrid.DataContextProperty, b);
                    }
                }
                //DataGridCheckBoxColumn acabat = new DataGridCheckBoxColumn();
                //acabat.Header = "Acabat";
                //dg.Columns.Add(acabat);


                //dg.Columns[0].Visibility = Visibility.Collapsed;

                i++;
            } 
        }

        private void dg_CellEditEnded(object sender, DataGridCellEditEndedEventArgs e)
        {
            DataGrid d = (DataGrid) sender;

            Linea_Comanda lc = (Linea_Comanda)d.SelectedItem;

            Linea_ComandaDB.Update(lc.Comanda.Codi, lc.Num, lc.Quantitat, lc.Acabat);
        }

        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            //_timer = new System.Threading.Timer(new System.Threading.TimerCallback((obj) => createDataGrids()), null, 0, 02000);
        }
    }
}
