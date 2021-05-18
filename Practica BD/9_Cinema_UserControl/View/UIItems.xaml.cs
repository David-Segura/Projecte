
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
using Windows.UI.Xaml.Shapes;

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace _9_Cinema_UserControl.View
{
    public sealed partial class UIItems : UserControl
    {
        public UIItems()
        {
            this.InitializeComponent();
        }



        public ItemEscenari Item
        {
            get { return (ItemEscenari)GetValue(ItemProperty); }
            set { SetValue(ItemProperty, value); }
        }

        // Using a DependencyProperty as the backing store for Item.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty ItemProperty =
            DependencyProperty.Register("Item", typeof(ItemEscenari), typeof(UIItems), new PropertyMetadata(null));



        public PointCollection PointList
        {
            get { return (PointCollection)GetValue(PointListProperty); }
            set { SetValue(PointListProperty, value); }
        }

        // Using a DependencyProperty as the backing store for PointList.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty PointListProperty =
            DependencyProperty.Register("PointList", typeof(PointCollection), typeof(UIItems), new PropertyMetadata(null));

        

    }
}
