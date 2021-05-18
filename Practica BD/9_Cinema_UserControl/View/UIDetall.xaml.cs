using CinemaDm;
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

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace _9_Cinema_UserControl.View
{
    public sealed partial class UIDetall : UserControl
    {
        public UIDetall()
        {
            this.InitializeComponent();
        }





        public ObservableCollection<Entrada> ListEntrades
        {
            get { return (ObservableCollection<Entrada>)GetValue(ListEntradesProperty); }
            set { SetValue(ListEntradesProperty, value); }
        }

        // Using a DependencyProperty as the backing store for ListEntrades.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty ListEntradesProperty =
            DependencyProperty.Register("ListEntrades", typeof(ObservableCollection<Entrada>), typeof(UIDetall), new PropertyMetadata(null));


        private void AddRow (ObservableCollection<Entrada> ListEntrades)
        {
            foreach (Entrada e in ListEntrades)
            {
                RowDefinition r = new RowDefinition();
                grdDetall.RowDefinitions.Add(r);
            }
        }

        private void FillRow (object sender, RoutedEventArgs e)
        {
            for(int i = 0; i < ListEntrades.Count; i++)
            {
                txbCadira.Text = ListEntrades[i].Ent_cad_num+"";
                txbPreu.Text = ListEntrades[i].Ent_preu+"";

            }
        }
    }
}
