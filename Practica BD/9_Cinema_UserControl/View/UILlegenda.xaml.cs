
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
    public sealed partial class UILlegenda : UserControl
    {
        public UILlegenda()
        {
            this.InitializeComponent();
        }

        

        public Categoria LaCategoria
        {
            get { return (Categoria)GetValue(LaCategoriaProperty); }
            set { SetValue(LaCategoriaProperty, value); }
        }

        // Using a DependencyProperty as the backing store for LaCategoria.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty LaCategoriaProperty =
            DependencyProperty.Register("LaCategoria", typeof(Categoria), typeof(UILlegenda), new PropertyMetadata(null,FondoCallback));

        private static void FondoCallback(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            UILlegenda ull = (UILlegenda)d;
            ull.FondoCallback();
        }

        private void FondoCallback()
        {
            
           
        }

        public Brush fondo
        {
            get { return (Brush)GetValue(fondoProperty); }
            set { SetValue(fondoProperty, value); }
        }

        // Using a DependencyProperty as the backing store for fondo.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty fondoProperty =
            DependencyProperty.Register("fondo", typeof(Brush), typeof(UILlegenda), new PropertyMetadata(null));



    }
}
