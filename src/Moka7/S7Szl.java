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
public class S7Szl {
    
    public int LENTHDR;
    public int N_DR;
    public int DataSize;
    public byte Data[];
    
    public S7Szl(int BufferSize)
    {
        Data = new byte[BufferSize];
    }
    protected void Copy(byte[] Src, int SrcPos, int DestPos, int Size)
    {
        System.arraycopy(Src, SrcPos, Data, DestPos, Size);
    }   
}
