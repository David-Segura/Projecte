
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
    public sealed partial class UITotal : UserControl
    {
        public UITotal()
        {
            this.InitializeComponent();

           
        }







        public Cadira LaCadira
        {
            get { return (Cadira)GetValue(LaCadiraProperty); }
            set { SetValue(LaCadiraProperty, value); }
        }

        // Using a DependencyProperty as the backing store for LaCadira.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty LaCadiraProperty =
            DependencyProperty.Register("LaCadira", typeof(Cadira), typeof(UITotal), new PropertyMetadata(null,IdCallback));
       

        private static void IdCallback(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            UITotal uitotal = (UITotal)d;
            uitotal.IdCallback();

        }

        private void IdCallback()
        {
            id = LaCadira.Id+"";
            //preu = LaCadira.Cat.Preu+" €";
        }



        public string id
        {
            get { return (string)GetValue(idProperty); }
            set { SetValue(idProperty, value); }
        }

        // Using a DependencyProperty as the backing store for id.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty idProperty =
            DependencyProperty.Register("id", typeof(string), typeof(UITotal), new PropertyMetadata(""));


        public string preu
        {
            get { return (string)GetValue(preuProperty); }
            set { SetValue(preuProperty, value); }
        }

        // Using a DependencyProperty as the backing store for id.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty preuProperty =
            DependencyProperty.Register("preu", typeof(string), typeof(UITotal), new PropertyMetadata(""));


    }
}
