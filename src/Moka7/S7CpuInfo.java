/**
 * This class was copied from the MOKA 7 Library, available on SourceForge.
 *
 * Original source: https://sourceforge.net/projects/snap7/files/Moka7/1.0.1/moka7-full-1.0.1.zip/download
 * Accessed on: 2024-05-01
 *
 * The original work is credited to the respective authors of the MOKA 7 Library.
 *
 * This file is included and used under the terms of the GNU General Public License v3.0 (GPL-3.0).
 * For the full license text, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Moka7;

/**
 *
 * @author Davide
 */
public class S7CpuInfo {
    
    private final int BufSize = 256;
    protected byte[] Buffer = new byte[BufSize];       

    protected void Update(byte[] Src, int Pos)
    {
        System.arraycopy(Src, Pos, Buffer, 0, BufSize);
    }   
    
    public String ModuleTypeName()
    {
        return S7.GetStringAt(Buffer,172,32);
    }
    public String SerialNumber()
    {
        return S7.GetStringAt(Buffer,138,24);
    }
    public String ASName()
    {
        return S7.GetStringAt(Buffer,2,24);
    }
    public String Copyright()
    {
        return S7.GetStringAt(Buffer,104,26);
    }
    public String ModuleName()
    {
        return S7.GetStringAt(Buffer,36,24);
    }
}
