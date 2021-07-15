package com.company;// show the usage of FFTJ-code for an "ideal" lowpass filter
//
import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;
import FFTJ.*;

public class Ideal_LP_FFTJ implements PlugInFilter {
    ImagePlus imp;

    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_ALL;
    }

    public void run(ImageProcessor ip) {
        String imTitle = imp.getTitle();


        fftj.FourierDomainOrigin fdOrigin = fftj.FourierDomainOrigin.AT_ZERO;
        fftj.ComplexValueType type = fftj.ComplexValueType.FREQUENCY_SPECTRUM_LOG;

        IJ.showStatus( "Calculating Fourier Transform ..." );
        fftj.FFT3D transformer = new fftj.SinglePrecFFT3D(imp.getStack(),  null );
        transformer.fft();
        // show frequency spectrum (logarithmic) with origin at image center:
        ImagePlus imp2 = transformer.toImagePlus( type, fftj.FourierDomainOrigin.AT_CENTER);
        imp2.show();

        // here comes the code to modify content in the frequency domain:
        for (int radius=25; radius<100; radius+=25) {
            // radius: cut off all wave numbers larger than radius
            ImagePlus real = transformer.toImagePlus( fftj.ComplexValueType.REAL_PART,fdOrigin);
            ImagePlus imag = transformer.toImagePlus( fftj.ComplexValueType.IMAG_PART,fdOrigin);
            int iw=real.getWidth();
            int ih=real.getHeight();
            int val=0;
            int i,k;
            for (k=-iw/2; k<iw/2; ++k) {
                for (i=-ih/2; i<ih/2; ++i) {
                    if (k*k+i*i>radius*radius) {
                        real.getProcessor().putPixel((k+iw)%iw,(i+ih)%ih,val);
                        imag.getProcessor().putPixel((k+iw)%iw,(i+ih)%ih,val);
                    }
                }
            }

            IJ.showStatus( "Calculating Inverse Fourier Transform ..." );
            fftj.FFT3D itransformer = new fftj.SinglePrecFFT3D(real.getStack(), imag.getStack() );
            itransformer.ifft();
            ImagePlus imp3 = itransformer.toImagePlus( fftj.ComplexValueType.REAL_PART);
            imp3.setTitle("Ideal LP"+radius+" for "+imTitle);
            imp3.show();
        } // radius


        return;

    }

}
