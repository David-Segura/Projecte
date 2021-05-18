using CinemaDm;
using CinemaDM;
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

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=234238

namespace _9_Cinema_UserControl
{
    /// <summary>
    /// Una página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class CompraEntrades : Page
    {
        public CompraEntrades()
        {
            this.InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            this.Frame.Navigate(typeof(MainPage));
        }
        ObservableCollection<Plat> llplats = PlatDB.getLlistaPlats();
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
             //f= (Funcio)e.Parameter;
            
        }
        private void GestioCarta_Loaded(object sender, RoutedEventArgs e)
        {
            ObservableCollection < Categoria > llc = CategoriaDB.getLlistaCategories();
            dtgCaracteristiques.ItemsSource =llc;
            dtgPlats.ItemsSource = llplats;
            cboCategoria.ItemsSource = llc;
            cboCategoria.DisplayMemberPath = "Nom";



        }
        
        public List <Entrada> list_entrades = new List<Entrada>();
        private void DtgCaracteristiques_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            DataGrid dg = (DataGrid)sender;
            Categoria actual = (Categoria)dg.SelectedItem;
            

            //lvCompres.ItemsSource = null;
            //lvCompres.ItemsSource = ent_seleccionades;
            //uiDetall.ListEntrades = ent_seleccionades;

            
            //Entrada entrada;
            //entrada = (Entrada)dtgEntrades.SelectedItem;
            //list_entrades.Add(entrada);
            //i++;
        }

        

       

        private void DtgPlats_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }

        private void BtnNetejaFiltre_Click(object sender, RoutedEventArgs e)
        {
            dtgCaracteristiques.SelectedIndex = -1;
            txbPlats.Text = "";

            llplats = PlatDB.getLlistaPlats();
            dtgPlats.ItemsSource = llplats;
        }

        private void BtnFiltra_Click(object sender, RoutedEventArgs e)
        {
            dtgCaracteristiques.RemoveFocusEngagement();
            Categoria actual = (Categoria)dtgCaracteristiques.SelectedItem;
            llplats = PlatDB.getLlistaPlats(actual==null?-1:actual.id, txbPlats.Text);
            dtgPlats.ItemsSource = llplats;
            dtgCaracteristiques.SelectedIndex = -1;
            txbPlats.Text = "";
        }

        private void BtnEsborrarPlat_Click(object sender, RoutedEventArgs e)
        {
            Plat perEsborrar = (Plat)dtgPlats.SelectedItem;
            PlatDB.Delete(perEsborrar);
            llplats.Remove(perEsborrar);
            dtgPlats.ItemsSource = llplats;
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {

        }

        private void BtnGuardarPlat_Click(object sender, RoutedEventArgs e)
        {
            Plat p = new Plat();
            p.Codi = PlatDB.getMaxCodi() + 1;
            p.Nom = txbNom.Text;
            p.DescripcioMD = txbDesc.Text;
            p.Preu = float.Parse(txbPreu.Text);
            p.Disponible = (bool)rdoDisponible.IsChecked;
            p.Categoria = cboCategoria.SelectedIndex + 1;

            PlatDB.Insert(p);
            llplats.Add(p);
            dtgPlats.ItemsSource = llplats;
        }
    }
}
