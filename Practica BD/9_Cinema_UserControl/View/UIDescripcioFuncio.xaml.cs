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

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace _9_Cinema_UserControl.View
{
    public sealed partial class UIDescripcioFuncio : UserControl
    {
        public UIDescripcioFuncio()
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
            DependencyProperty.Register("UnEspectacle", typeof(Espectacle), typeof(UIDescripcioFuncio), new PropertyMetadata(null));


       
        public Funcio UnaFuncio
        {
            get { return (Funcio)GetValue(UnaFuncioProperty); }
            set { SetValue(UnaFuncioProperty, value); }
        }

        // Using a DependencyProperty as the backing store for UnaFuncio.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty UnaFuncioProperty =
            DependencyProperty.Register("UnaFuncio", typeof(Funcio), typeof(UIDescripcioFuncio), new PropertyMetadata(null));




        public Plat UnaSala
        {
            get { return (Plat)GetValue(UnaSalaProperty); }
            set { SetValue(UnaSalaProperty, value); }
        }

        // Using a DependencyProperty as the backing store for UnaSala.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty UnaSalaProperty =
            DependencyProperty.Register("UnaSala", typeof(Plat), typeof(UIDescripcioFuncio), new PropertyMetadata(null));



        private void UcDescripcioFuncio_Loaded(object sender, RoutedEventArgs e)
        {
            int q_localitats = EntradaDB.GetLocalitats();


            txbLoc.Text = q_localitats+" disponibles";

        }
    }
}
