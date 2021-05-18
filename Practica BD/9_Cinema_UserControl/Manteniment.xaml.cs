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
    public sealed partial class Manteniment : Page
    {
        public Manteniment()
        {
            this.InitializeComponent();
        }

       
        private ObservableCollection<Espectacle> espectacles;
        private ObservableCollection<Categoria> categories;
        
        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            espectacles = EspectacleDB.getLlistaEspectacles();
            categories = CategoriaDB.getLlistaCategories();
           

            dtgEspectacles.ItemsSource = null;
            
            cboCat.DisplayMemberPath = "Nom";
            cboCat.ItemsSource = categories;

            

            dtgEspectacles.ItemsSource = espectacles;
        }

        private void Filtra_Click(object sender, RoutedEventArgs e)
        {
            string nom_desc = txbFiltre.Text;
            // Recuperem el codi d'espectacle, en el cas que el combo estigui buit, desem un -1.
            int numCat = cboCat.SelectedItem != null ?
                ((Categoria)cboCat.SelectedItem).Id : -1;

            dtgEspectacles.ItemsSource = null;
            
            dtgEspectacles.ItemsSource = EspectacleDB.getLlistaEspectacles(nom_desc, numCat);
        }

        private void BtnPagina_Click(object sender, RoutedEventArgs e)
        {
            this.Frame.Navigate(typeof(MainPage));
        }

        private void DtgEspectacles_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Espectacle esp = ((Espectacle)dtgEspectacles.SelectedItem);
            if (esp != null)
            {
                Espectacle clonic = new Espectacle(esp);
                uiManteniment.UnEspectacle = clonic;
            }

        }

        private void Button_Save_Click(object sender, RoutedEventArgs e)
        {
            string nom =     uiManteniment.UnEspectacle.Esp_nom;
            DateTime inici = uiManteniment.UnEspectacle.Esp_data_inici; 
            DateTime fi=     uiManteniment.UnEspectacle.Esp_data_fi; 
            int sal_id =     uiManteniment.UnEspectacle.Esp_sal_id;
            int cae_id =     uiManteniment.UnEspectacle.Esp_cae_id;
            string desc =    uiManteniment.UnEspectacle.Esp_desc;


            
            if (EspectacleDB.Update(((Espectacle)dtgEspectacles.SelectedItem).Esp_id,
                            nom,
                            inici,fi,sal_id,cae_id,desc))
            {

               // Espectacle esp = ((Espectacle)dtgEspectacles.SelectedItem);
               // esp.Esp_nom =        (string)uiManteniment.UnEspectacle.Esp_nom;
               // esp.Esp_data_inici = (DateTime)uiManteniment.UnEspectacle.Esp_data_inici;
               // esp.Esp_data_fi =    (DateTime)uiManteniment.UnEspectacle.Esp_data_fi; 
               // esp.Esp_sal_id =     uiManteniment.UnEspectacle.Esp_sal_id;
               // esp.Esp_cae_id =     uiManteniment.UnEspectacle.Esp_cae_id;
               // esp.Esp_desc =       uiManteniment.UnEspectacle.Esp_desc;
                
               // dtgEspectacles.ItemsSource = EspectacleDB.getLlistaEspectacles();
                
            }
            else
            {
                String missatgeError = "Error";
                

                

            }

            dtgEspectacles.ItemsSource = null;
            dtgEspectacles.ItemsSource = EspectacleDB.getLlistaEspectacles();

        }

        private void Button_Discard_Click(object sender, RoutedEventArgs e)
        {
            Espectacle esp = ((Espectacle)dtgEspectacles.SelectedItem);
            if (esp != null)
            {
                Espectacle clonic = new Espectacle(esp);
                uiManteniment.UnEspectacle = clonic;
            }
        }

        private void Button_Esborrar_Click(object sender, RoutedEventArgs e)
        {
            if (dtgEspectacles.SelectedItem != null)
            {
                
                if (EspectacleDB.Delete((Espectacle)dtgEspectacles.SelectedItem))
                {
                    espectacles.RemoveAt(dtgEspectacles.SelectedIndex);
                }
                else
                {
                    String error = "Error fatal esborrant l'espectacle";
                   
                    
                }
            }
        }
    }
}
