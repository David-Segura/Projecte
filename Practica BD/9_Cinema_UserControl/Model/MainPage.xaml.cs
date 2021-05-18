using _9_Cinema_UserControl.View;
using CinemaDm;
using CinemaDM;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Storage;
using Windows.Storage.Pickers;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=402352&clcid=0xc0a

namespace _9_Cinema_UserControl
{
    /// <summary>
    /// Página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class MainPage : Page

    {

        /// -------------------------------------------------------------------------------------------------------
        /// FILE PICKER
        /// Obrir un selector d'arxius, triar un arxiu i copiar-lo a la carpeta ApplicationData del
        /// programa. Crear una imatge en memòria a partir de l'arxiu.
        private async void BtnFile1_Click(object sender, RoutedEventArgs e)
        {
            FileOpenPicker fp = new FileOpenPicker();
            fp.FileTypeFilter.Add(".jpg");
            fp.FileTypeFilter.Add(".png");

            StorageFile sf = await fp.PickSingleFileAsync();
            // Cerca la carpeta de dades de l'aplicació, dins de ApplicationData
            var folder = ApplicationData.Current.LocalFolder;
            // Dins de la carpeta de dades, creem una nova carpeta "icons"
            var iconsFolder = await folder.CreateFolderAsync("icons", CreationCollisionOption.OpenIfExists);
            // Creem un nom usant la data i hora, de forma que no poguem repetir noms.
            string name = (DateTime.Now).ToString("yyyyMMddhhmmss") + "_" + sf.Name;
            // Copiar l'arxiu triat a la carpeta indicada, usant el nom que hem muntat
            StorageFile copiedFile = await sf.CopyAsync(iconsFolder, name);
            // Crear una imatge en memòria (BitmapImage) a partir de l'arxiu copiat a ApplicationData
            BitmapImage tmpBitmap = new BitmapImage(new Uri(copiedFile.Path));
            // ..... YOUR CODE HERE ...........
        }


        decimal total=new decimal ();
        public MainPage()
        {
            this.InitializeComponent();

            

        }   

        int i = 0;
        int j = 0;
        List<UITotal> uitList = new List<UITotal>();

        private void Uicad_CadiraSelectionChanged(object sender, EventArgs e)
        {
            UICadira c = (UICadira)sender;


                UITotal uit = new UITotal();
            
            if (c.LaCadira.Estat == EnumEstat.SELECCIONADA)
            {
                Canvas.SetLeft(uit, 600);
                Canvas.SetTop(uit, 100 + i * 30);
                i = i + 1;

                uit.id = c.LaCadira.Id.ToString();
               // uit.preu = c.LaCadira.Cat.Preu.ToString() + "€";
                //total = total + c.LaCadira.Cat.Preu;

                uitList.Add(uit);
            }
            else
            {
                foreach(UITotal u in uitList)
                {
                    if(u.id == c.LaCadira.Id.ToString())
                    {
                       
                        if (uitList.Count ==1) j = 0;
                        break;
                    }
                    j++;
             
                }
                uitList.RemoveAt(j);
                j = 0;
                //total = total - c.LaCadira.Cat.Preu;
            }
            i = 0;
            cnvTotal.Children.Clear();
            
           foreach(UITotal ui in uitList)
           {

                cnvTotal.Children.Add(uitList[i]);
                i++;
           }



            
            
            

            //txbTotal.Text = total+" €";

           
        }

        private void BtnSave_Click(object sender, RoutedEventArgs e)
        {

        }
        private ObservableCollection<Categoria> categories;
        private ObservableCollection<Cadira> cadires;
        private ObservableCollection<Unitat> unitats;
        private ObservableCollection<Espectacle> espectacles;
        private ObservableCollection<Funcio> funcions;
        private ObservableCollection<Ingredient> preus_categories_espectacles;
        private ObservableCollection<Plat> sales;
        private ObservableCollection<Entrada> entrades;
        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            espectacles = EspectacleDB.getLlistaEspectacles();
            categories = CategoriaDB.getLlistaCategories();
            cadires = CadiraDB.getLlistaCadires();
            unitats = UnitatDB.GetLlistaUnitats();
           
            preus_categories_espectacles = IngredientDB.getLlistaPreus();
            sales= PlatDB.getLlistaPlats();
            entrades= EntradaDB.GetLlistaEntrades();

            dtgEspectacles.ItemsSource = null;
            dtgFuncions.ItemsSource = null;
            cboCat.DisplayMemberPath = "Nom";
            cboCat.ItemsSource = categories;
            dtgFuncions.ItemsSource = funcions;
            dtgEspectacles.ItemsSource = espectacles;
        }

        private void Filtra_Click(object sender, RoutedEventArgs e)
        {
            string nom_desc = txbFiltre.Text;
            // Recuperem el codi d'espectacle, en el cas que el combo estigui buit, desem un -1.
            int numCat = cboCat.SelectedItem != null ?
                ((Categoria)cboCat.SelectedItem).Id : -1;

            dtgEspectacles.ItemsSource = null;
            dtgFuncions.ItemsSource = null;
           
            dtgEspectacles.ItemsSource = EspectacleDB.getLlistaEspectacles(nom_desc, numCat);
        }

        private void BtnPagina_Click(object sender, RoutedEventArgs e)
        {
            Funcio f = (Funcio)dtgFuncions.SelectedItem;
            this.Frame.Navigate(typeof(CompraEntrades),f);
        }

        private void BtnManteniment_Click(object sender, RoutedEventArgs e)
        {
            Espectacle esp = ((Espectacle)dtgEspectacles.SelectedItem);
            this.Frame.Navigate(typeof(Manteniment),esp);
        }

        private void NoFiltra_Click(object sender, RoutedEventArgs e)
        {
            dtgEspectacles.ItemsSource = null;
            dtgFuncions.ItemsSource = null;
           
            dtgEspectacles.ItemsSource = EspectacleDB.getLlistaEspectacles();
        }

        private void DtgEspectacles_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Espectacle esp = ((Espectacle)dtgEspectacles.SelectedItem);
            dtgFuncions.ItemsSource = FuncioDB.getLlistaFuncions(esp.Esp_id);
        }
    }
}
