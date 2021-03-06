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

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=234238

namespace GestioRestaurant
{
    /// <summary>
    /// Una página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class PaginaCarta : Page
    {
        public PaginaCarta()
        {
            this.InitializeComponent();
        }

        private void BtnBack_Click(object sender, RoutedEventArgs e)
        {
            Frame.Navigate(typeof(GestioCarta));
        }

        private void CtrlBrowser_NavigationStarting(WebView sender, WebViewNavigationStartingEventArgs args)
        {

        }

        private void CtrlBrowser_NavigationCompleted(WebView sender, WebViewNavigationCompletedEventArgs args)
        {

        }

        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            String sURL = "http://51.68.224.27:8080/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Fdam2-dsegura&reportUnit=%2Fdam2-dsegura%2FCarta&standAlone=true&j_username=dam2-dsegura&j_password=47120851S";

            if (sURL.IndexOf("http://") == -1) { sURL = "http://" + sURL; }

            //</ get URL >



            try

            {

                //--< load website >--

                Uri webURL = new Uri(sURL);

                ctlBrowser.Navigate(webURL);

                //--</ load website >--

            }

            catch (Exception)

            {

                throw;

            }
        }
    }
}
